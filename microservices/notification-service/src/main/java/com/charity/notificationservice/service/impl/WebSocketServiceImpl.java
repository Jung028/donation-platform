package com.charity.notificationservice.service.impl;

import com.charity.notificationservice.dto.NotificationResponse;
import com.charity.notificationservice.model.Notification;
import com.charity.notificationservice.service.WebSocketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * Implementation of the WebSocketService interface.
 * This class is responsible for sending real-time notifications to
 * connected clients via WebSocket.
 * 
 * @author Charity Platform Team
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class WebSocketServiceImpl implements WebSocketService {
    
    /**
     * Spring's messaging template for sending messages to WebSocket destinations.
     */
    private final SimpMessagingTemplate messagingTemplate;
    
    @Override
    public boolean sendNotification(Notification notification) {
        try {
            Long recipientId = notification.getRecipientId();
            
            if (recipientId == null) {
                log.warn("No recipient ID provided for notification ID: {}", notification.getId());
                return false;
            }
            
            // Convert notification to response DTO for client consumption
            NotificationResponse response = mapToResponse(notification);
            
            // Send to user-specific topic
            String destination = "/topic/user/" + recipientId + "/notifications";
            log.info("Sending WebSocket notification to destination: {}", destination);
            
            messagingTemplate.convertAndSend(destination, response);
            log.info("WebSocket notification sent successfully");
            return true;
        } catch (Exception e) {
            log.error("Failed to send WebSocket notification: {}", e.getMessage(), e);
            return false;
        }
    }
    
    @Override
    public boolean sendSimpleMessage(Long userId, Object message) {
        try {
            String destination = "/topic/user/" + userId + "/messages";
            log.info("Sending simple WebSocket message to destination: {}", destination);
            
            messagingTemplate.convertAndSend(destination, message);
            log.info("Simple WebSocket message sent successfully");
            return true;
        } catch (Exception e) {
            log.error("Failed to send simple WebSocket message: {}", e.getMessage(), e);
            return false;
        }
    }
    
    @Override
    public boolean broadcastMessage(Object message) {
        try {
            String destination = "/topic/broadcast";
            log.info("Broadcasting WebSocket message to all users");
            
            messagingTemplate.convertAndSend(destination, message);
            log.info("Broadcast message sent successfully");
            return true;
        } catch (Exception e) {
            log.error("Failed to broadcast WebSocket message: {}", e.getMessage(), e);
            return false;
        }
    }
    
    /**
     * Maps a notification entity to a response DTO.
     * 
     * @param notification The notification entity
     * @return The notification response DTO
     */
    private NotificationResponse mapToResponse(Notification notification) {
        return NotificationResponse.builder()
                .id(notification.getId())
                .type(notification.getType())
                .channel(notification.getChannel())
                .recipientId(notification.getRecipientId())
                .subject(notification.getSubject())
                .content(notification.getContent())
                .status(notification.getStatus())
                .metadata(notification.getMetadata())
                .actionUrl(notification.getActionUrl())
                .createdAt(notification.getCreatedAt())
                .sentAt(notification.getSentAt())
                .deliveredAt(notification.getDeliveredAt())
                .readAt(notification.getReadAt())
                .build();
    }
}
