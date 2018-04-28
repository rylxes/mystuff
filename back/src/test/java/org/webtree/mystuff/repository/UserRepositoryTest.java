package org.webtree.mystuff.repository;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.webtree.mystuff.AbstractSpringTest;
import org.webtree.mystuff.boot.App;
import org.webtree.mystuff.domain.User;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class UserRepositoryTest extends AbstractSpringTest {
    private static final String USERNAME = "testUser";
    @Rule
    public ClearGraphDBRule clearGraphDBRule = new ClearGraphDBRule();

    @Autowired
    private UserRepository userRepository;

    @Test
    public void whenEmptyRepository_shouldNotReturnUser() throws Exception {
        userRepository.findByUsername(USERNAME);
    }

    @Test
    public void whenAddUser_shouldReturnItByName() throws Exception {
        User user = User.builder().username(USERNAME).build();
        userRepository.save(user);

        assertThat(userRepository.findByUsername(USERNAME)).isEqualTo(user);
    }

}