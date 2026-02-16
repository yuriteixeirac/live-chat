package com.edu.ifrn.livechat.controllers;

import com.edu.ifrn.livechat.DTOs.UserDTO;
import com.edu.ifrn.livechat.services.UserService;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserDTO> aboutMe(Principal principal) {
        UserDTO userDTO = new UserDTO(
                userService.findByUsername(principal.getName())
        );

        return ResponseEntity.ok(userDTO);
    }

    @GetMapping
    @RolesAllowed("ADMIN")
    public ResponseEntity<List<UserDTO>> findAll() {
        return ResponseEntity.ok(userService.findAll()
                .stream()
                .map(UserDTO::new)
                .toList()
        );
    }

    @RolesAllowed("ADMIN")
    @GetMapping("/{username}")
    public ResponseEntity<UserDTO> findByUsername(@PathVariable String username) {
        return ResponseEntity.ok(new UserDTO(userService.findByUsername(username)));
    }
}
