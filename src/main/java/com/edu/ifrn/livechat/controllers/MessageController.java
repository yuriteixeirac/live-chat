package com.edu.ifrn.livechat.controllers;

import com.edu.ifrn.livechat.DTOs.MessageDTO;
import com.edu.ifrn.livechat.exceptions.UserAlreadyExistsException;
import com.edu.ifrn.livechat.exceptions.UserDoesNotExistException;
import com.edu.ifrn.livechat.models.Message;
import com.edu.ifrn.livechat.services.MessageService;
import com.edu.ifrn.livechat.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/messages")
public class MessageController {
    private final UserService userService;
    private final MessageService messageService;

    public MessageController(UserService userService, MessageService messageService) {
        this.userService = userService;
        this.messageService = messageService;
    }

    @GetMapping("/{username}")
    public ResponseEntity<Page<MessageDTO>> findMessagesWith(
            Principal principal,
            @PathVariable String username,
            @PageableDefault(size = 150, sort = "sentAt", direction = Sort.Direction.DESC) Pageable pageable
            ) {
        if (!userService.existsByUsername(username)) {
            throw new UserDoesNotExistException("Usuário não existe.");
        }

        Page<MessageDTO> messages = messageService.findMessagesBetweenUsers(
                principal.getName(), username, pageable
        ).map(MessageDTO::new);

        return ResponseEntity.ok(messages);
    }
}
