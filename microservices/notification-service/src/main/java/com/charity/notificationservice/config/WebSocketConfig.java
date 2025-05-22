package com.charity.notificationservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * Configuration class for WebSocket functionality.
 * This class sets up WebSocket endpoints and message broker configuration
 * to enable real-time notifications in the application.
 * 
 * @author Charity Platform Team
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    
    /**
     * Configures the message broker for WebSocket communication.
     * Sets up destination prefixes for messages sent from server to clients.
     * 
     * @param registry The MessageBrokerRegistry to configure
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // Prefix for server-to-client messages
        // Clients subscribe to these destinations to receive messages
        registry.enableSimpleBroker("/topic");
        
        // Prefix for client-to-server messages
        // Clients send messages to these destinations
        registry.setApplicationDestinationPrefixes("/app");
    }
    
    /**
     * Registers STOMP endpoints for WebSocket connections.
     * These are the endpoints that clients connect to initially.
     * 
     * @param registry The StompEndpointRegistry to configure
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Register the "/ws" endpoint, enabling SockJS fallback options
        registry.addEndpoint("/ws")
                // Allow connections from other domains (cross-origin)
                .setAllowedOriginPatterns("*")
                // Enable SockJS fallback for browsers that don't support WebSocket
                .withSockJS();
    }
}
