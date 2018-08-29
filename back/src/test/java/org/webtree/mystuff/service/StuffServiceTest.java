package org.webtree.mystuff.service;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.webtree.mystuff.boot.App;
import org.webtree.mystuff.model.domain.Category;
import org.webtree.mystuff.model.domain.Stuff;
import org.webtree.mystuff.model.domain.User;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
@Ignore("#30 neo4j")
public class StuffServiceTest extends AbstractServiceTest {
    private static final long ID = 123L;
    private static final String NAME = "test name";
    private static final String CATEGORY_1 = "category1";
    private static final String CATEGORY_2 = "category2";

    private Category existingCategory;
    private User existingUser;

    @Before
    public void setUp() throws Exception {
        existingCategory = addCategory("existingCategory");
        existingUser = addUser("existingUser");
    }

    @Test
    public void testAddAndGetStuff() throws Exception {
        Category sc1 = createCategory(CATEGORY_1);
        Category sc2 = createCategory(CATEGORY_2);
        assertThat(stuffService.getById(ID)).isNull();
        Stuff stuff = Stuff.builder().name(NAME).category(sc1).category(sc2).build();
        Stuff addedStuff = stuffService.save(stuff);
        assertThat(addedStuff.getId()).isNotNull();
        Stuff byId = stuffService.getById(addedStuff.getId());
        assertThat(byId).isNotNull();
        assertThat(byId.getName()).isEqualTo(NAME);
        assertThat(byId.getCategories()).isEqualTo(stuff.getCategories());
    }

    @Test
    public void whenCreate_shouldReturnItWithId() {
        Stuff stuff = stuffService.create(Stuff.builder().name(NAME).build(), existingUserId(), existingCategoryId());
        assertThat(stuff).isNotNull();
        assertThat(stuff.getId()).isGreaterThan(-1);
        assertThat(stuff.getCategories()).isEmpty();
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenTryToCreateStuffWithId_shouldThrowException() {
        stuffService.create(Stuff.builder().id(1L).name(NAME).build(), existingUserId(), existingCategoryId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenTryToCreateWithCreator_shouldThrowException() {
        stuffService.create(Stuff.builder().name(NAME).creator(User.builder().build()).build(), existingUserId(), existingCategoryId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenTryToCreateWithCategory_shouldThrowException() {
        stuffService.create(Stuff.builder().name(NAME).category(Category.builder().build()).build(), existingUserId(), existingCategoryId());
    }

    @Test
    public void whenCreateWithCategories_shouldHaveRelationship() {
        Stuff stuff = stuffService.create(
            Stuff.builder().name(NAME).build(),
            existingUserId(),
            existingCategoryId());
        Set<Category> categories = stuffService.getById(stuff.getId()).getCategories();
        assertThat(categories).hasSize(1);
        assertThat(categories).contains(existingCategory);
    }

    @Test
    public void whenCreateWithCreator_shouldHaveRelationship() {
        Stuff stuff = stuffService.create(
            Stuff.builder().name(NAME).build(),
            existingUserId(),
            existingCategoryId());
        User creator = stuffService.getById(stuff.getId()).getCreator();
        assertThat(creator).isEqualTo(existingUser);
    }

    @Test
    public void whenCreateWithCreator_shouldUse() {
        Stuff stuff = stuffService.create(
            Stuff.builder().name(NAME).build(),
            existingUserId(),
            existingCategoryId());
        Set<User> users = stuffService.getById(stuff.getId()).getUsers();
        assertThat(users).containsExactly(existingUser);
    }

    private HashSet<Long> existingCategoryId() {
        return Sets.newHashSet(existingCategory.getId());
    }

    private Long existingUserId() {
        return existingUser.getId();
    }
}