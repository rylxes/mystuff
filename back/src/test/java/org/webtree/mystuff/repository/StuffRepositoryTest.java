package org.webtree.mystuff.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.common.collect.Sets;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.webtree.mystuff.AbstractSpringTest;
import org.webtree.mystuff.boot.App;
import org.webtree.mystuff.model.domain.Category;
import org.webtree.mystuff.model.domain.Stuff;
import org.webtree.mystuff.model.domain.User;

import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class StuffRepositoryTest extends AbstractSpringTest {
    public static final String STUFF_NAME = "stuff name";
    public static final String CATEGORY_NAME = "category name";
    public static final String USER_NAME = "user name";
    @Autowired
    private StuffRepository stuffRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldAddCategoryRelationship() {
        Stuff stuff = stuffRepository.save(Stuff.Builder.create().withName(STUFF_NAME).build());
        Category category = categoryRepository.save(Category.Builder.create().withName(CATEGORY_NAME).build());
        stuffRepository.addRelationshipsWithCategories(Sets.newHashSet(category.getId()), stuff.getId());

        Stuff stuffInRepo = stuffRepository.findOne(stuff.getId());
        Set<Category> categories = stuffInRepo.getCategories();
        assertThat(categories).hasSize(1);
        assertThat(categories).contains(category);
    }

    @Test
    public void shouldAddUsersRelationship() {
        Stuff stuff = stuffRepository.save(Stuff.Builder.create().withName(STUFF_NAME).build());
        User user = userRepository.save(User.Builder.create().withUsername(USER_NAME).build());
        stuffRepository.addRelationshipsWithUsers(Sets.newHashSet(user.getId()), stuff.getId());

        Set<User> categories = stuffRepository.findOne(stuff.getId()).getUsers();
        assertThat(categories).hasSize(1);
        assertThat(categories).contains(user);
    }

    @Test
    public void shouldAddCreateRelation() {
        Stuff stuff = stuffRepository.save(Stuff.Builder.create().withName(STUFF_NAME).build());
        User user = userRepository.save(User.Builder.create().withUsername(USER_NAME).build());
        stuffRepository.addCreateRelationship(user.getId(), stuff.getId());

        assertThat(stuffRepository.findOne(stuff.getId()).getCreator()).isEqualTo(user);
    }
}