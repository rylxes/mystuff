package org.webtree.mystuff.controller;

import org.junit.Rule;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.webtree.mystuff.model.domain.User;
import org.webtree.mystuff.security.JwtTokenUtil;
import org.webtree.mystuff.service.UserService;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SecurityControllerTest extends AbstractControllerTest {
    private static final String TEST_USERNAME = "testUser";
    private static final String TEST_PASS = "testPass";
    @Rule
    public ClearGraphDBRule clearGraphDBRule = new ClearGraphDBRule();

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private UserService userService;
    @Autowired
    private JwtTokenUtil tokenUtil;

    @Test
    public void whenLoginWithCorrectUser_shouldReturnValidToken() throws Exception {
        User user = User.builder().username(TEST_USERNAME).password(TEST_PASS).build();
        userService.add(user);

        MvcResult mvcResult = mockMvc.perform(
            post("/rest/token/new")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user))
        )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.errors").doesNotExist())
            .andReturn();
        String token = mvcResult.getResponse().getContentAsString();
        assertThat(tokenUtil.validateToken(token, user)).isTrue();
    }

    @Test
    @WithAnonymousUser
    public void whenRefreshWithInvalidToken_shouldReturnError() throws Exception {
        mockMvc.perform(get("/rest/token/refresh"))
            .andExpect(status().isUnauthorized());
    }

    @Test
    public void whenLoginWithIncorrectUsername_shouldReturnErrorMessage() throws Exception {
        User wrongUsernameUser = User.builder().username("wrong").password(TEST_PASS).build();

        ResultActions actions = mockMvc.perform(
            post("/rest/token/new")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(wrongUsernameUser))
        );

        assertUnauthorized(actions);
    }

    @Test
    public void whenLoginWithIncorrectPassword_shouldReturnErrorMessage() throws Exception {
        User user = User.builder().username(TEST_USERNAME).password(TEST_PASS).build();
        userService.add(user);
        User wrongPasswordUser = User.builder().username(TEST_USERNAME).password("123").build();

        ResultActions actions = mockMvc.perform(
            post("/rest/token/new")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(wrongPasswordUser))
        );

        assertUnauthorized(actions);
    }

    private void assertUnauthorized(ResultActions resultActions) throws Exception {
        String errorMessage = messageSource.getMessage("login.badCredentials", null, Locale.getDefault());

        resultActions
            .andExpect(status().is(401))
            .andExpect(jsonPath("$").value(errorMessage));

    }
}