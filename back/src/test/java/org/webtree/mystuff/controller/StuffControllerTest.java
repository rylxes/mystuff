package org.webtree.mystuff.controller;

import com.google.common.collect.Sets;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MvcResult;
import org.webtree.mystuff.domain.Stuff;
import org.webtree.mystuff.domain.User;
import org.webtree.mystuff.security.WithMockCustomUser;
import org.webtree.mystuff.service.StuffService;
import org.webtree.mystuff.service.UserService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockCustomUser
public class StuffControllerTest extends BaseControllerTest {
    private static final String NAME = "name example";
    private static final String USER_1 = "user1";
    private static final String USER_2 = "user2";

    @Rule
    public ClearGraphDBRule clearGraphDBRule = new ClearGraphDBRule();

    @SpyBean
    public StuffService stuffService;
    @SpyBean
    public UserService userService;

    @Test
    public void whenAddStuff_shouldReturnNewId() throws Exception {
        Stuff stuff = buildNewStuff(NAME, USER_1);
        MvcResult mvcResult = mockMvc.perform(
            post("/rest/stuff")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(stuff))
        )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.errors").doesNotExist())
            .andExpect(jsonPath("$.id").isNumber())
            .andExpect(jsonPath("$.name").value(NAME))
            .andExpect(jsonPath("$.users", hasSize(1)))
            .andExpect(jsonPath("$.users[0].username").value(USER_1))
            .andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void whenGetStuff_shouldReturnItFromService() throws Exception {
        stuffService.save(Stuff.builder().id(1L).name(NAME).build());

        mockMvc.perform(get("/rest/stuff/1").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.errors").doesNotExist())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.name").value(NAME));
    }

    @Test
    public void whenGetStuffList_shouldReturnListOfStuffs() throws Exception {
        stuffService.save(buildNewStuff(NAME, USER_1));
        stuffService.save(buildNewStuff(NAME + 2, USER_1));

        mockMvc.perform(get("/rest/stuff/list").contentType(MediaType.APPLICATION_JSON))
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

        mockMvc.perform(get("/rest/stuff/list").contentType(MediaType.APPLICATION_JSON))
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
        mockMvc.perform(get("/rest/stuff/list").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isUnauthorized());
        mockMvc.perform(post("/rest/stuff").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isUnauthorized());

        Stuff stuff = buildNewStuff(NAME, USER_1);
        mockMvc.perform(get("/rest/stuff/1", objectMapper.writeValueAsString(stuff)).contentType(MediaType.APPLICATION_JSON))
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
                .contentType(MediaType.APPLICATION_JSON)
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

    private Stuff buildNewStuff(String name, String username) {
        return Stuff.builder().users(Sets.newHashSet(User.builder().username(username).build())).name(name).build();
    }

}