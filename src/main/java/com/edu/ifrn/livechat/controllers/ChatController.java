package com.edu.ifrn.livechat.controllers;

import com.edu.ifrn.livechat.DTOs.MessageDTO;
import com.edu.ifrn.livechat.models.Message;
import com.edu.ifrn.livechat.models.User;
import com.edu.ifrn.livechat.services.MessageService;
import com.edu.ifrn.livechat.services.UserService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.time.Instant;

@Controller
public class ChatController {
    private SimpMessagingTemplate messagingTemplate;
    private MessageService messageService;
    private UserService userService;

    public ChatController(
            SimpMessagingTemplate messagingTemplate,
            UserService userService,
            MessageService messageService
    ) {
        this.messagingTemplate = messagingTemplate;
        this.userService = userService;
        this.messageService = messageService;
    }

    @MessageMapping("/private")
    public void sendPrivate(MessageDTO messageDTO, Principal principal) {
        Message message = new Message();

        User sender = userService.findByUsername(principal.getName());
        User recipient = userService.findByUsername(messageDTO.getTo());

        message.setFrom(sender);
        message.setTo(recipient);
        message.setContent(messageDTO.getContent());
        message.setSentAt(Instant.now());

        messageService.save(message);

        MessageDTO recipientDTO = new MessageDTO();
        recipientDTO.setFrom(sender.getUsername());
        recipientDTO.setTo(recipient.getUsername());
        recipientDTO.setContent(message.getContent());
        recipientDTO.setSentAt(message.getSentAt());

        messagingTemplate.convertAndSendToUser(
            recipient.getUsername(),
            "/queue/private",
            recipientDTO
        );
    }
}
