package com.edu.ifrn.livechat.controllers;

import com.edu.ifrn.livechat.DTOs.TokenDTO;
import com.edu.ifrn.livechat.models.User;
import com.edu.ifrn.livechat.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<TokenDTO> register(@RequestBody User user) {
        return ResponseEntity.ok(userService.register(user));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody User user) {
        return ResponseEntity.ok(userService.login(user));
    }
}
