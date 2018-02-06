package org.webtree.mystuff.controller;

import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
        return stuffService.save(stuff);
    }

    @GetMapping("/{id}")
    public Stuff get(@PathVariable long id) {
        return stuffService.getById(id);
    }

    @GetMapping("/list")
    public Iterable<Stuff> getList(Principal principal) {
        return stuffService.getUserStuff(principal.getName());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id, Principal principal) {
        stuffService.delete(id, principal.getName());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/addExisting")
    public ResponseEntity<?> addExisting(@RequestHeader long id, Authentication authentication) {
        Stuff stuff = stuffService.getById(id);
        stuff.getUsers().add((User) authentication.getPrincipal());
        stuffService.save(stuff);
        return ResponseEntity.ok().build();
    }
}
