package org.webtree.mystuff.controller;

import com.merapar.graphql.controller.GraphQlControllerImpl;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.webtree.mystuff.boot.App;
import org.webtree.mystuff.domain.Stuff;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { App.class })
@WebMvcTest(StuffController.class)
public class StuffControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenAddStuff_shouldReturnNewId() throws Exception {
        JSONObject jsonObject = new JSONObject();
        String name = "name example";
        Stuff stuff = Stuff.builder().name(name).build();
        jsonObject.put("stuff", stuff);
        mockMvc.perform(
            post("/rest/stuff/add")
            .content(jsonObject.toString())
        )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.errors").doesNotExist())
            .andExpect(jsonPath("$.data.id").isNotEmpty())
            .andExpect(jsonPath("$.data.name").value(name));
    }
}