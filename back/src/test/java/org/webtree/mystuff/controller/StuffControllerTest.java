package org.webtree.mystuff.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.webtree.mystuff.boot.App;
import org.webtree.mystuff.domain.Stuff;
import org.webtree.mystuff.service.StuffService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
@AutoConfigureMockMvc
public class StuffControllerTest {
    private static final String NAME = "name example";
    @Autowired
    private MockMvc mockMvc;

    @SpyBean
    public StuffService stuffService;

    @Test
    public void whenAddStuff_shouldReturnNewId() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Stuff stuff = Stuff.builder().name(NAME).build();
        mockMvc.perform(
            post("/rest/stuff")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(stuff))
        )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.errors").doesNotExist())
            .andExpect(jsonPath("$.id").isNumber())
            .andExpect(jsonPath("$.name").value(NAME));
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
}