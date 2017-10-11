package org.webtree.mystuff.controller;

import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.webtree.mystuff.domain.Stuff;
import org.webtree.mystuff.domain.User;
import org.webtree.mystuff.service.StuffService;

import java.security.Principal;

@RestController
@RequestMapping("/rest/stuff")
@CrossOrigin
public class StuffController {
    private final StuffService stuffService;

    @Autowired public StuffController(StuffService stuffService) {
        this.stuffService = stuffService;
    }

    @PostMapping
    public Stuff add(@RequestBody Stuff stuff, Authentication authentication) {
        stuff.setUsers(Sets.newHashSet((User) authentication.getPrincipal()));
        return stuffService.addStuff(stuff);
    }

    @GetMapping("/{id}")
    public Stuff get(@PathVariable Long id) {
        return stuffService.getById(id);
    }

    @GetMapping("/list")
    public Iterable<Stuff> getList(Principal principal) {
        return stuffService.getUserStuff(principal.getName());
    }
}
