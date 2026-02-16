package com.edu.ifrn.livechat.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@Slf4j
public class WebSocketEventListener {

    @EventListener
    public void handleConnection(SessionConnectedEvent event) {
        StompHeaderAccessor acessor = StompHeaderAccessor.wrap(event.getMessage());
        System.out.printf("\nNEW USER CONNECTED: %s\n\n", event.getUser().getName());
    }

    @EventListener
    public void handleDisconnection(SessionDisconnectEvent event) {
        StompHeaderAccessor acessor = StompHeaderAccessor.wrap(event.getMessage());
        System.out.printf("\nUSER DISCONNECTED: %s\n\n", event.getUser().getName());
    }
}
