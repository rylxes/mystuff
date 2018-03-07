package org.webtree.mystuff.service;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.webtree.mystuff.BaseSpringTest;
import org.webtree.mystuff.boot.App;
import org.webtree.mystuff.domain.User;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class UserServiceTest extends BaseSpringTest {
    private static final String USERNAME = "testUser";
    @Rule
    public ClearGraphDBRule clearGraphDBRule = new ClearGraphDBRule();
    @Autowired
    private UserService userService;

    @Test
    public void testSaveAndGet() throws Exception {
        User addedUser = addUser();
        assertThat(addedUser).isNotNull();
        assertThat(addedUser.getId()).isGreaterThanOrEqualTo(0);

        assertThat(userService.getById(addedUser.getId())).isEqualTo(addedUser);
    }

    @Test
    public void testGetByUsername() throws Exception {
        assertThat(userService.loadUserByUsername(USERNAME)).isNull();
    }

    @Test
    public void shouldReturnUserByUsername() throws Exception {
        addUser();
        User user = userService.loadUserByUsername(USERNAME);
        assertThat(user).isNotNull();
        assertThat(user.getUsername()).isEqualTo(USERNAME);
    }

    private User addUser() {
        return userService.add(User.builder().username(USERNAME).build());
    }
}