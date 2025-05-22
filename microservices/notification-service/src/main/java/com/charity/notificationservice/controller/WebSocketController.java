package com.charity.notificationservice.controller;

import com.charity.notificationservice.dto.NotificationRequest;
import com.charity.notificationservice.dto.NotificationResponse;
import com.charity.notificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Map;

/**
 * Controller for handling WebSocket messages.
 * This controller handles messages sent from clients via WebSocket
 * and can send responses back through the messaging template.
 * 
 * @author Charity Platform Team
 */
@Controller
@RequiredArgsConstructor
@Slf4j
public class WebSocketController {
    
    private final SimpMessagingTemplate messagingTemplate;
    private final NotificationService notificationService;
    
    /**
     * Handles messages sent to the "/app/notification" destination.
     * Clients can use this to send notifications through WebSocket instead of REST.
     * 
     * @param request The notification request sent by the client
     */
    @MessageMapping("/notification")
    public void processNotification(@Payload NotificationRequest request) {
        log.info("Received WebSocket notification request: {}", request);
        NotificationResponse response = notificationService.sendNotification(request);
        
        // Send confirmation to the sender
        String replyDestination = "/topic/notification/confirmation";
        messagingTemplate.convertAndSend(replyDestination, Map.of(
                "status", "processed",
                "notificationId", response.getId()
        ));
    }
    
    /**
     * Handles client acknowledgments of notifications.
     * Clients can use this to mark notifications as read via WebSocket.
     * 
     * @param payload A map containing the notification ID to mark as read
     */
    @MessageMapping("/notification/ack")
    public void acknowledgeNotification(@Payload Map<String, String> payload) {
        String notificationId = payload.get("notificationId");
        log.info("Received WebSocket acknowledgment for notification: {}", notificationId);
        
        if (notificationId != null) {
            notificationService.markAsRead(notificationId);
        }
    }
}
