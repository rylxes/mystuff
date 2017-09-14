package org.webtree.mystuff.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.webtree.mystuff.domain.Stuff;
import org.webtree.mystuff.service.StuffService;

@RestController
@RequestMapping("/rest/stuff")
public class StuffController {
    private final StuffService stuffService;

    @Autowired public StuffController(StuffService stuffService) {
        this.stuffService = stuffService;
    }

    @PostMapping("/add")
    public Stuff addStuff(@RequestBody Stuff stuff) {
        return stuffService.addStuff(stuff);
    }
}
