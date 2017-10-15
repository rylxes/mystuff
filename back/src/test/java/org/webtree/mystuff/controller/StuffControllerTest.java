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

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockCustomUser
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
        stuffService.addStuff(Stuff.builder().id(1L).name(NAME).build());

        mockMvc.perform(get("/rest/stuff/1").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.errors").doesNotExist())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.name").value(NAME));
    }

    @Test
    public void whenGetStuffList_shouldReturnListOfStuffs() throws Exception {
        stuffService.addStuff(buildNewStuff(NAME, USER_1));
        stuffService.addStuff(buildNewStuff(NAME + 2, USER_1));

        mockMvc.perform(get("/rest/stuff/list").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.errors").doesNotExist())
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].id").isNumber())
            .andExpect(jsonPath("$[1].id").isNumber());
    }

    @Test
    public void whenGetStuffList_shouldReturnOnlyUsersStuff() throws Exception {
        stuffService.addStuff(buildNewStuff(NAME, USER_1));
        stuffService.addStuff(buildNewStuff(NAME + 2, USER_1 + 2));

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