package org.webtree.mystuff.controller;

import org.junit.Rule;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.webtree.mystuff.domain.User;
import org.webtree.mystuff.service.UserService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest extends BaseControllerTest {
    private static final String USERNAME = "testUser";
    @Rule
    public ClearGraphDBRule clearGraphDBRule = new ClearGraphDBRule();
    @Autowired
    private UserService userService;

    @Test
    public void whenRegisterUser_shouldCreateNewOne() throws Exception {
        mockMvc.perform(post("/rest/user/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(User.builder().username(USERNAME).build()))
        )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.errors").doesNotExist());
        User user = userService.loadUserByUsername(USERNAME);
        assertThat(user).isNotNull();
        assertThat(user.getUsername()).isEqualTo(USERNAME);
    }
}