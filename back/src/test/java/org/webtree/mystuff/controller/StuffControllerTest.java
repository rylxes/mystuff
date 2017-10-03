package org.webtree.mystuff.controller;

import org.junit.Test;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;
import org.webtree.mystuff.domain.Stuff;
import org.webtree.mystuff.service.StuffService;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser
public class StuffControllerTest extends BaseControllerTest {
    private static final String NAME = "name example";

    @SpyBean
    public StuffService stuffService;

    @Test
    public void whenAddStuff_shouldReturnNewId() throws Exception {
        Stuff stuff = Stuff.builder().name(NAME).build();
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
    public void whenGetStuffList_shouldReturnListOfStuffs() throws Exception {
        String name2 = NAME + "2";
        addStuff(Stuff.builder().name(NAME).build());
        addStuff(Stuff.builder().name(name2).build());

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
    @WithAnonymousUser
    public void whenAnonymousUser_shouldResponseForbidden() throws Exception {
        mockMvc.perform(get("/rest/stuff/list").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isUnauthorized());
        mockMvc.perform(post("/rest/stuff").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isUnauthorized());

        Stuff stuff = Stuff.builder().name(NAME).build();
        mockMvc.perform(get("/rest/stuff/1", objectMapper.writeValueAsString(stuff)).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isUnauthorized());
    }
}