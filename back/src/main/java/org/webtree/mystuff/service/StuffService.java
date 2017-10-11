package org.webtree.mystuff.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webtree.mystuff.domain.Stuff;
import org.webtree.mystuff.repository.StuffRepository;

@Service
public class StuffService {
    private final StuffRepository stuffRepository;

    @Autowired
    public StuffService(StuffRepository stuffRepository) {
        this.stuffRepository = stuffRepository;
    }

    public Stuff addStuff(Stuff stuff) {
        return stuffRepository.save(stuff);
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
}
