package org.webtree.mystuff.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.webtree.mystuff.domain.User;
import org.webtree.mystuff.service.UserService;

@RestController
@RequestMapping("rest/user")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @Transactional
    public ResponseEntity<?> register(@RequestBody User user) {
        if (userService.loadUserByUsername(user.getUsername()) != null) {
            return ResponseEntity.badRequest().body("User already exists");
        } else {
            return ResponseEntity.ok(userService.add(user));
        }
    }
}
