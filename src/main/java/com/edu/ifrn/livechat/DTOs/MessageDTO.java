package com.edu.ifrn.livechat.DTOs;

import com.edu.ifrn.livechat.models.Message;
import jakarta.annotation.security.DenyAll;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {
    private String content;
    private String from;
    private String to;
    private Instant sentAt;

    public MessageDTO(Message message) {
        this.content = message.getContent();
        this.from = message.getFrom().getUsername();
        this.to = message.getTo().getUsername();
        this.sentAt = message.getSentAt();
    }
}
