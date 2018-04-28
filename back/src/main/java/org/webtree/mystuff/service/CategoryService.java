package org.webtree.mystuff.service;

import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webtree.mystuff.domain.Category;
import org.webtree.mystuff.repository.CategoryRepository;

import java.util.List;
import java.util.Set;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;

    }

    public List<Category> getCategoriesBySearchString(String searchString) {
        return categoryRepository.getCategoriesBySearchString(searchString);
    }

    public Category findByName(String name) {
        return categoryRepository.getByName(name);
    }

    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    public Category getById(long id) {
        return categoryRepository.findOne(id);
    }

    public Category testSave(Category category) {
        return categoryRepository.save(category);
    }


    public Set<Category> getCategories(Set<Long> categories) {
        return Sets.newHashSet(categoryRepository.findAll(categories));
    }
}
