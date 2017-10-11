package org.webtree.mystuff.controller;

import com.google.common.collect.Sets;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;
import org.webtree.mystuff.domain.Stuff;
import org.webtree.mystuff.domain.User;
import org.webtree.mystuff.service.StuffService;

import java.security.Principal;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser
public class StuffControllerTest extends BaseControllerTest {
    private static final String NAME = "name example";
    private static final String USER_1 = "user1";

    @Rule
    public ClearGraphDBRule clearGraphDBRule = new ClearGraphDBRule();

    @SpyBean
    public StuffService stuffService;

    @Test
    public void whenAddStuff_shouldReturnNewId() throws Exception {
        Stuff stuff = buildNewStuff(NAME, USER_1);
        addStuff(stuff)
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.errors").doesNotExist())
            .andExpect(jsonPath("$.id").isNumber())
            .andExpect(jsonPath("$.name").value(NAME));
    }

    private ResultActions addStuff(Stuff stuff) throws Exception {
        return mockMvc.perform(
            post("/rest/stuff")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(stuff))
        );
    }

    @Test
    public void whenGetStuff_shouldReturnItFromService() throws Exception {
        stuffService.addStuff(Stuff.builder().id(1L).name(NAME).build());

        mockMvc.perform(get("/rest/stuff/1").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.errors").doesNotExist())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.name").value(NAME));
    }

    @Test
    @WithMockUser
    public void whenGetStuffList_shouldReturnListOfStuffs(Principal principal) throws Exception {
        String name2 = NAME + "2";
        String username = principal.getName();
        addStuff(buildNewStuff(NAME, username));
        addStuff(buildNewStuff(name2, username));

        mockMvc.perform(get("/rest/stuff/list").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.errors").doesNotExist())
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].id").isNumber())
            .andExpect(jsonPath("$[1].id").isNumber())
            .andExpect(jsonPath("$[0].name").value(NAME))
            .andExpect(jsonPath("$[1].name").value(name2));
    }

    @Test
    @WithMockUser(USER_1)
    public void whenGetStuffList_shouldReturnOnlyUsersStuff() throws Exception {
        addStuff(buildNewStuff(NAME + 1, USER_1));
        addStuff(buildNewStuff(NAME + 2, "user2"));

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
    }

    private Stuff buildNewStuff(String name, String username) {
        return Stuff.builder().users(Sets.newHashSet(User.builder().username(username).build())).name(name).build();
    }
}