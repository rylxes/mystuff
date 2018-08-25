package org.webtree.mystuff.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.webtree.mystuff.AbstractSpringTest;
import org.webtree.mystuff.model.domain.Category;
import org.webtree.mystuff.model.domain.User;

public abstract class AbstractServiceTest extends AbstractSpringTest {
    @Autowired
    protected StuffService stuffService;
    @Autowired
    protected UserService userService;
    @Autowired
    protected CategoryService categoryService;

    protected Category createCategory(String categoryName) {
        return Category.builder().name(categoryName).build();
    }

    private User createUser(String username) {
        return User.builder().username(username).build();
    }

    protected User addUser(String username) {
        return userService.add(createUser(username));
    }

    protected Category addCategory(String category) {
        return categoryService.save(createCategory(category));
    }
}
