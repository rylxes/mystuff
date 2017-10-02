package org.webtree.mystuff.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.webtree.mystuff.domain.AuthDetails;
import org.webtree.mystuff.domain.User;
import org.webtree.mystuff.security.JwtTokenUtil;
import org.webtree.mystuff.service.UserService;

@RestController
@RequestMapping("/rest")
public class SecurityController {
    private final AuthenticationManager authenticationManager;

    private JwtTokenUtil jwtTokenUtil;
    private UserService userService;

    @Autowired
    public SecurityController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
    }

    @PostMapping("${jwt.route.authentication.path}")
    public ResponseEntity<?> login(@RequestBody AuthDetails authDetails, Device device) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                authDetails.getUsername(), authDetails.getPassword()
            )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = userService.loadUserByUsername(authDetails.getUsername());

        return ResponseEntity.ok(jwtTokenUtil.generateToken(user, device));
    }
}
