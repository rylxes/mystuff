package org.webtree.mystuff.service;

import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webtree.mystuff.domain.Stuff;
import org.webtree.mystuff.repository.StuffRepository;

import java.util.Set;

@Service
public class StuffService {
    private final StuffRepository stuffRepository;
    private final CategoryService categoryRepository;

    @Autowired
    public StuffService(StuffRepository stuffRepository, CategoryService categoryRepository) {
        this.stuffRepository = stuffRepository;
        this.categoryRepository = categoryRepository;
    }

    /**
     * Save stuff and dependencies
     */
    public Stuff save(Stuff stuff) {
        return stuffRepository.save(stuff);
    }

    /**
     * Save stuff with depth 0 and add connections
     *
     * @param stuff      All relationship fields should be null. Made for avoid of errors.
     * @param creator    id of user created this stuff
     * @param categories selected categories for create relationship
     * @return just created stuff with id
     */
    @Transactional
    public Stuff create(Stuff stuff, Long creator, Set<Long> categories) {
        Preconditions.checkArgument(stuff.getId() == null, "You can't create stuff with id");
        Preconditions.checkArgument(stuff.getCreator() == null, "Creator should be given in parameters");
        Preconditions.checkArgument(stuff.getCategories() == null || stuff.getCategories().isEmpty(), "Categories should be given in parameters");

        stuff = stuffRepository.save(stuff, 0);
        stuffRepository.addRelationshipsWithCategories(categories, stuff.getId());
        stuffRepository.addCreateRelationship(creator, stuff.getId());
        return stuff;
    }

    public Stuff getById(long id) {
        return stuffRepository.findOne(id);
    }

    public Iterable<Stuff> getList() {
        return stuffRepository.findAll();
    }

    public Iterable<Stuff> getUserStuff(String username) {
        return stuffRepository.getUserStuff(username);
    }

    public void delete(Long id, String username) {
        stuffRepository.delete(id);
    }
}
