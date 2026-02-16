package com.edu.ifrn.livechat.config;

import com.edu.ifrn.livechat.exceptions.InvalidAuthenticationHeaderException;
import com.edu.ifrn.livechat.exceptions.InvalidTokenException;
import com.edu.ifrn.livechat.services.CustomUserDetailsService;
import com.edu.ifrn.livechat.services.JWTService;
import io.jsonwebtoken.JwtException;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class WebSocketAuthInterceptor implements ChannelInterceptor {
    private final JWTService jwtService;
    private final CustomUserDetailsService userDetailsService;

    public WebSocketAuthInterceptor(JWTService jwtService, CustomUserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
         StompHeaderAccessor accessor = StompHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

         if (accessor == null || !StompCommand.CONNECT.equals(accessor.getCommand())) {
             return message;
         }

         String authHeader = accessor.getFirstNativeHeader("Authorization");
         System.out.println();
         System.out.println(accessor.getSessionAttributes());
         System.out.println();
         System.out.println(authHeader);

         if (authHeader == null || !authHeader.startsWith("Bearer ")) {
             System.out.println("TOMA AI TUA HEADER RECEBIDA " + authHeader);
             throw new InvalidAuthenticationHeaderException("Header de autenticação inválida.");
         }

         String token = authHeader.substring(7);
         if (token.isEmpty()) {
             throw new InvalidTokenException("Token is empty.");
         }

         String username;

         try {
            username = jwtService.extractUsername(token);
         } catch (JwtException e) {
             throw new InvalidTokenException(e.getMessage());
         }

         if (username == null) {
             throw new InvalidTokenException("Token sem subject.");
         }

         if (!jwtService.isValid(token, username)) {
             throw new InvalidTokenException("Token inválido.");
         }

         UserDetails userDetails = userDetailsService.loadUserByUsername(username);

         UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                 userDetails, null, userDetails.getAuthorities()
         );

         accessor.setUser(authentication);
         return message;
    }
}
