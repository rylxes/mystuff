package org.webtree.mystuff.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.google.common.collect.Sets;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MvcResult;
import org.webtree.mystuff.domain.Category;
import org.webtree.mystuff.domain.Stuff;
import org.webtree.mystuff.domain.User;
import org.webtree.mystuff.restModel.CreateStuff;
import org.webtree.mystuff.security.WithMockCustomUser;
import org.webtree.mystuff.service.CategoryService;
import org.webtree.mystuff.service.StuffService;
import org.webtree.mystuff.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.refEq;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockCustomUser
public class StuffControllerTest extends AbstractControllerTest {
    private static final String NAME = "stuff name example";
    private static final String USER_1 = "user1";
    private static final String USER_2 = "user2";
    private static final String CATEGORY1 = "category1";
    private static final String CATEGORY2 = "category2";
    @Rule
    public ClearGraphDBRule clearGraphDBRule = new ClearGraphDBRule();

    @SpyBean
    public StuffService stuffService;
    @SpyBean
    public UserService userService;
    @SpyBean
    public CategoryService categoryService;
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    @Captor
    private ArgumentCaptor<Set<Long>> categoriesCaptor;

    @Test
    public void whenAddStuff_shouldReturnNewId() throws Exception {
        Stuff stuff = Stuff.builder().name(NAME).build();
        mockMvc.perform(
            post("/rest/stuff")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(CreateStuff.builder().stuff(stuff).build()))
        )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.errors").doesNotExist())
            .andExpect(jsonPath("$.id").isNumber())
            .andExpect(jsonPath("$.name").value(NAME));
    }

    @Test
    public void whenGetStuff_shouldReturnItFromService() throws Exception {
        stuffService.save(Stuff.builder().id(1L).name(NAME).build());
        mockMvc.perform(get("/rest/stuff/1").contentType(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.errors").doesNotExist())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.name").value(NAME));
    }

    @Test
    public void whenGetStuffList_shouldReturnListOfStuffs() throws Exception {
        stuffService.save(buildNewStuff(NAME, USER_1));
        stuffService.save(buildNewStuff(NAME + 2, USER_1));

        mockMvc.perform(get("/rest/stuff/list").contentType(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.errors").doesNotExist())
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].id").isNumber())
            .andExpect(jsonPath("$[1].id").isNumber());
    }

    @Test
    public void whenGetStuffList_shouldReturnOnlyUsersStuff() throws Exception {
        stuffService.save(buildNewStuff(NAME, USER_1));
        stuffService.save(buildNewStuff(NAME + 2, USER_1 + 2));

        mockMvc.perform(get("/rest/stuff/list").contentType(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.errors").doesNotExist())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].id").isNumber())
            .andExpect(jsonPath("$[0].users", hasSize(1)))
            .andExpect(jsonPath("$[0].users[0].username").value(USER_1));
    }

    @Test
    @WithAnonymousUser
    public void whenAnonymousUser_shouldResponseForbidden() throws Exception {
        mockMvc.perform(get("/rest/stuff/list").contentType(APPLICATION_JSON))
            .andExpect(status().isUnauthorized());
        mockMvc.perform(post("/rest/stuff").contentType(APPLICATION_JSON))
            .andExpect(status().isUnauthorized());

        Stuff stuff = buildNewStuff(NAME, USER_1);
        mockMvc.perform(get("/rest/stuff/1", objectMapper.writeValueAsString(stuff)).contentType(APPLICATION_JSON))
            .andExpect(status().isUnauthorized());
        mockMvc.perform(delete("/rest/stuff/" + stuff.getId()))
            .andExpect(status().isUnauthorized());
    }

    @Test
    public void whenDeleteStuff_shouldNotReturnItForUser() throws Exception {
        Stuff stuff = stuffService.save(buildNewStuff(NAME, USER_1));

        mockMvc.perform(delete("/rest/stuff/" + stuff.getId()))
            .andExpect(status().isOk());
        mockMvc.perform(get("/rest/stuff/list"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.errors").doesNotExist())
            .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @WithAnonymousUser
    public void whenAddExistingStuff_shouldReturnForBothUsers() throws Exception {
        Stuff stuff = stuffService.save(buildNewStuff(NAME, USER_1));
        User user2 = userService.add(User.builder().username(USER_2).password("pass").build());
        MvcResult mvcResult = mockMvc.perform(
            post("/rest/token/new")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user2))
        )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.errors").doesNotExist())
            .andReturn();
        String user2token = mvcResult.getResponse().getContentAsString();

        mockMvc.perform(post("/rest/stuff/addExisting")
            .header("Authorization", "Bearer " + user2token)
            .header("id", stuff.getId()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.errors").doesNotExist());

        Stuff updatedStuff = stuffService.getById(stuff.getId());
        assertThat(updatedStuff.getUsers().size()).isEqualTo(2);
        assertThat(updatedStuff.getUsers()).containsExactlyInAnyOrder(user2, userService.loadUserByUsername(USER_1));
    }


    @Test
    public void whenAddStuffCategory_shouldSaveCorrect() throws Exception {
        CreateStuff createStuff = CreateStuff.builder()
            .stuff(Stuff.builder().name(NAME).build())
            .categories(buildNewStaffCategories(CATEGORY1, CATEGORY2).stream().map(Category::getId).collect(Collectors.toSet()))
            .build();
        MvcResult mvcResult = mockMvc.perform(
            post("/rest/stuff")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createStuff))
        )
            .andExpect(status().isOk())
            .andReturn();

        String postResponse = mvcResult.getResponse().getContentAsString();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Stuff savedStaff = objectMapper.readValue(postResponse, Stuff.class);

        Stuff stuffFromService = stuffService.getById(savedStaff.getId());
        assertThat(stuffFromService.getCategories()).hasSize(2);
        Set<String> expectedCategories = Sets.newHashSet(CATEGORY1, CATEGORY2);
        stuffFromService.getCategories().forEach(category -> assertThat(expectedCategories).contains(category.getName()));
    }

    @Test
    public void whenReadStuff_shouldReturnCorrectFromService() throws Exception {
        Stuff stuff = stuffService.save(buildNewStuffWithStaffCategory(NAME, USER_1, buildNewStaffCategories(CATEGORY1)));
        mockMvc.perform(get("/rest/stuff/" + stuff.getId()).contentType(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.categories").isArray())
            .andExpect(jsonPath("$.categories").isNotEmpty())
            .andExpect(jsonPath("$.categories[0].name").value(CATEGORY1))
            .andExpect(jsonPath("$.categories[1]").doesNotExist());
    }

    @Test
    public void whenRequestCategoriesBySearchString_shouldReturnCorrect() throws Exception {
        String query = "cat";
        User user = addUser();
        Category sc1 = categoryService.save(Category.builder().name("ater").creator(user).build());
        Category sc2 = categoryService.save(Category.builder().name("catfg").creator(user).build());
        Category sc3 = categoryService.save(Category.builder().name("cat").creator(user).build());
        Category sc4 = categoryService.save(Category.builder().name("catui").creator(user).build());
        Category sc5 = categoryService.save(Category.builder().name("catgf").creator(user).build());

        mockMvc.perform(get("/rest/stuff/category/names?startsFrom=" + query).contentType(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.[0].name").value(sc2.getName()))
            .andExpect(jsonPath("$.[1].name").value(sc3.getName()))
            .andExpect(jsonPath("$.[2].name").value(sc4.getName()))
            .andExpect(jsonPath("$.[3].name").value(sc5.getName()));

    }

    @Test
    public void whenAddStuffWithCategories_shouldCallCreateOnService() throws Exception {
        Stuff stuff = Stuff.builder().name(NAME).build();
        Set<Long> categories = Sets.newHashSet(1L, 2L, 3L);
        mockMvc.perform(post("/rest/stuff").contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(CreateStuff.builder().stuff(stuff).categories(categories).build()))
        );
        verify(stuffService).create(refEq(stuff, "id", "categories"), anyLong(), categoriesCaptor.capture());
        assertThat(categoriesCaptor.getValue()).isEqualTo(categories);
    }

    @Test
    public void whenAddExistingCategory_shouldReturnIt_andDonNotTryToCreate() throws Exception {
        Category category = categoryService.save(Category.builder().name(CATEGORY1).build());
        reset(categoryService);
        mockMvc.perform(post("/rest/stuff/category")
            .contentType(APPLICATION_JSON)
            .param("categoryName", CATEGORY1)
        )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(category.getId()))
            .andExpect(jsonPath("$.name").value(CATEGORY1));
        verify(categoryService, never()).save(any());
    }

    private Stuff buildNewStuff(String name, String username) {
        return Stuff.builder().users(buildNewUsers(username)).name(name).build();
    }

    private List<Category> buildNewStaffCategories(String... categoryNames) {
        User user = addUser();
        List<Category> categories = new ArrayList<>();
        for (String categoryName : categoryNames) {
            Category category = categoryService.save(Category.builder().name(categoryName).creator(user).build());
            categories.add(category);
        }
        return categories;

    }

    private User addUser() {
        return userService.add(User.builder().username(USER_1).build());
    }

    private Set<User> buildNewUsers(String username) {
        return Sets.newHashSet(userService.add(User.builder().username(username).build()));
    }


    private Stuff buildNewStuffWithStaffCategory(String name, String username, List<Category> categories) {
        return Stuff.builder().users(buildNewUsers(username)).name(name)
            .categories(categories).build();
    }


}