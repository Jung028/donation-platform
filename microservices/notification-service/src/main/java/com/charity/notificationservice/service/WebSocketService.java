package com.charity.notificationservice.service;

import com.charity.notificationservice.model.Notification;

/**
 * Service interface for WebSocket notification delivery.
 * This interface defines the functionality for sending real-time notifications
 * to connected clients via WebSocket.
 * 
 * @author Charity Platform Team
 */
public interface WebSocketService {
    
    /**
     * Sends a notification via WebSocket.
     * The implementation should handle formatting the notification for WebSocket
     * and delivering it to the appropriate user's topic.
     * 
     * @param notification The notification to send
     * @return true if the notification was sent successfully, false otherwise
     */
    boolean sendNotification(Notification notification);
    
    /**
     * Sends a simple message to a specific user via WebSocket.
     * This is a utility method for sending messages without creating a full notification entity.
     * 
     * @param userId The ID of the user to receive the message
     * @param message The message to send
     * @return true if the message was sent successfully, false otherwise
     */
    boolean sendSimpleMessage(Long userId, Object message);
    
    /**
     * Broadcasts a message to all connected clients.
     * This is useful for system-wide announcements or global notifications.
     * 
     * @param message The message to broadcast
     * @return true if the broadcast was successful, false otherwise
     */
    boolean broadcastMessage(Object message);
}
