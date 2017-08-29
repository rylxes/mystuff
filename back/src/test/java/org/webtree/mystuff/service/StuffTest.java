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
import java.util.LinkedHashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = GraphQlConf.class)
@WebMvcTest(GraphQlControllerImpl.class)
public class StuffTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StuffFetcher stuffFetcher;

    @Before
    public void setUp() throws Exception {
        stuffFetcher.stuffList.put(1L, new Stuff().setId(1L).setName("stuff 1"));
        stuffFetcher.stuffList.put(2L, new Stuff().setId(2L).setName("stuff 2"));
    }

    @Test
    public void get() throws Exception {
        // Given
        val query = "{ "
            + "stuffList {"
            + "  id"
            + " }"
            + "}";

        // When
        val postResult = performGraphQlPost(query);

        // Then
        postResult.andExpect(status().isOk())
            .andExpect(jsonPath("$.errors").doesNotExist())
            .andExpect(jsonPath("$.data.stuffList[0].id").value(1))
            .andExpect(jsonPath("$.data.stuffList[1].id").value(2))
        ;
    }

    @Test
    public void add() throws Exception {
        // Given
        val query = "mutation addStuff($input: addStuffInput!) {"
            + "  addStuff(input: $input) {"
            + "    id"
            + "    name"
            + "  }\n"
            + "}";

        val params = new LinkedHashMap<String, Object>();
        long key = 123;
        String value = "test stuff";
        params.put("id", key);
        params.put("name", value);

        // When
        val result = performGraphQlPost(query, params);

        // Then
        result.andExpect(status().isOk())
            .andExpect(jsonPath("$.errors").doesNotExist())
            .andExpect(jsonPath("$.data.addStuff.id").value(key))
            .andExpect(jsonPath("$.data.addStuff.name").value(value));

        assertThat(stuffFetcher.stuffList.containsKey(key));
    }
    @Test
    public void update() throws Exception {
        // Given
        val query = "mutation updateStuff($input: updateStuffInput!) {" +
            "  updateStuff(input: $input) {" +
            "    id" +
            "    name" +
            "  }\n" +
            "}";

        val variables = new LinkedHashMap<>();
        long key = 1;
        String value = "updated stuff";
        variables.put("id", key);
        variables.put("name", value);

        // When
        val postResult = performGraphQlPost(query, variables);

        // Then
        postResult.andExpect(status().isOk())
            .andExpect(jsonPath("$.errors").doesNotExist())
            .andExpect(jsonPath("$.data.updateStuff.id").value(key))
            .andExpect(jsonPath("$.data.updateStuff.name").value(value));

        assertThat(stuffFetcher.stuffList.get(key).getName()).isEqualTo(value);
    }

    @Test
    public void deleteRole() throws Exception {
        // Given
        val query = "mutation deleteStuff($input: deleteStuffInput!) {" +
            "  deleteStuff(input: $input) {" +
            "    id" +
            "    name" +
            "  }\n" +
            "}";

        val variables = new LinkedHashMap<>();
        variables.put("id", 2);

        // When
        val postResult = performGraphQlPost(query, variables);

        // Then
        postResult.andExpect(status().isOk())
            .andExpect(jsonPath("$.errors").doesNotExist())
            .andExpect(jsonPath("$.data.deleteStuff.id").value(2))
            .andExpect(jsonPath("$.data.deleteStuff.name").value("stuff 2"));

        assertThat(stuffFetcher.stuffList).containsKeys(1L);
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
