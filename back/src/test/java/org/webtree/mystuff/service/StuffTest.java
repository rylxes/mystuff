package org.webtree.mystuff.service;

import com.merapar.graphql.controller.GraphQlControllerImpl;
import lombok.val;
import net.minidev.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.webtree.mystuff.domain.Stuff;
import org.webtree.mystuff.fetcher.StuffFetcher;

import java.util.Collections;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConf.class)
@WebMvcTest(GraphQlControllerImpl.class)
public class StuffTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StuffFetcher stuffFetcher;


    @Before
    public void setUp() throws Exception {
        stuffFetcher.stuffList.put(1L, new Stuff().setId(1L));
        stuffFetcher.stuffList.put(2L, new Stuff().setId(2L));
    }

    @Test
    public void getStuff() throws Exception {
        // Given
        val query = "{ "
            + "stuffList {"
            + "  id"
            + " }"
            + "}";

        // When
        val postResult = performGraphQlPost(query);

        // Then
        postResult
            .andExpect(jsonPath("$.errors").doesNotExist())
            .andExpect(jsonPath("$.data.stuffList[0].id").value(1))
            .andExpect(jsonPath("$.data.stuffList[1].id").value(2))
        ;
    }

    private ResultActions performGraphQlPost(String query) throws Exception {
        return performGraphQlPost(query, null);
    }

    private ResultActions performGraphQlPost(String query, Map variables) throws Exception {
        return mockMvc.perform(post("/v1/graphql")
            .contentType(MediaType.APPLICATION_JSON)
            .content(generateRequest(query, variables))
        );
    }

    private String generateRequest(String query, Map variables) {
        val jsonObject = new JSONObject();
        jsonObject.put("query", query);
        if (variables != null) {
            jsonObject.put("variables", Collections.singletonMap("input", variables));
        }
        return jsonObject.toString();
    }

}
