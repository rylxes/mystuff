package org.webtree.mystuff.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.webtree.mystuff.domain.Stuff;
import org.webtree.mystuff.service.StuffService;

@RestController
@RequestMapping("/rest/stuff")
@CrossOrigin
public class StuffController {
    private final StuffService stuffService;

    @Autowired public StuffController(StuffService stuffService) {
        this.stuffService = stuffService;
    }

    @PostMapping
    public Stuff add(@RequestBody Stuff stuff) {
        return stuffService.addStuff(stuff);
    }

    @GetMapping("/{id}")
    public Stuff get(@PathVariable Long id) {
        return stuffService.getById(id);
    }

    @GetMapping("/list")
    public Iterable<Stuff> getList() {
        return stuffService.getList();
    }
}
