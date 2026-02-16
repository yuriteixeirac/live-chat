package com.edu.ifrn.livechat.services;

import com.edu.ifrn.livechat.models.Message;
import com.edu.ifrn.livechat.repositories.MessageRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    private MessageRepository messageRepository;
    private UserService userService;

    public MessageService(MessageRepository messageRepository, UserService userService) {
        this.messageRepository = messageRepository;
        this.userService = userService;
    }

    public void save(Message message) {
        messageRepository.save(message);
    }

    public Page<Message> findMessagesBetweenUsers(String firstUser, String secondUser, Pageable pageable) {
        return messageRepository.findMessagesBetweenUsers(firstUser, secondUser, pageable);
    }
}
