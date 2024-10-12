package com.anubhavauth.messagingprac.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // Set up a simple message broker for subscriptions at "/topic"
        config.enableSimpleBroker("/topic");
        // Prefix for incoming message mappings from clients
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Set up a WebSocket endpoint at "/ws" and allow SockJS fallback
        registry.addEndpoint("/ws").setAllowedOrigins("*");
    }
}
