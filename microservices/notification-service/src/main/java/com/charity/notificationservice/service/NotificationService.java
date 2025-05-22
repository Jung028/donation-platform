package com.charity.notificationservice.service;

import com.charity.notificationservice.dto.NotificationBulkRequest;
import com.charity.notificationservice.dto.NotificationRequest;
import com.charity.notificationservice.dto.NotificationResponse;
import com.charity.notificationservice.model.NotificationChannel;
import com.charity.notificationservice.model.NotificationType;

import java.util.List;

/**
 * Service interface for notification management operations.
 * This interface defines the core functionality for creating, sending,
 * and managing notifications throughout the system.
 * 
 * @author Charity Platform Team
 */
public interface NotificationService {
    
    /**
     * Creates and sends a notification based on the provided request.
     * The notification will be delivered through the specified channel
     * or the recipient's preferred channel if none is specified.
     * 
     * @param request The notification request containing all necessary details
     * @return The created notification response with delivery status
     */
    NotificationResponse sendNotification(NotificationRequest request);
    
    /**
     * Sends the same notification to multiple recipients.
     * This is a batch operation that creates individual notifications for each recipient.
     * 
     * @param request The bulk notification request containing recipient IDs and notification details
     * @return List of created notification responses with delivery statuses
     */
    List<NotificationResponse> sendBulkNotifications(NotificationBulkRequest request);
    
    /**
     * Retrieves all notifications for a specific user.
     * 
     * @param userId The ID of the user whose notifications to retrieve
     * @return List of notifications for the specified user
     */
    List<NotificationResponse> getNotificationsForUser(Long userId);
    
    /**
     * Retrieves all unread notifications for a specific user.
     * 
     * @param userId The ID of the user whose unread notifications to retrieve
     * @return List of unread notifications for the specified user
     */
    List<NotificationResponse> getUnreadNotificationsForUser(Long userId);
    
    /**
     * Marks a notification as read.
     * 
     * @param notificationId The ID of the notification to mark as read
     * @return The updated notification response
     */
    NotificationResponse markAsRead(String notificationId);
    
    /**
     * Marks all notifications for a user as read.
     * 
     * @param userId The ID of the user whose notifications to mark as read
     * @return The number of notifications marked as read
     */
    int markAllAsRead(Long userId);
    
    /**
     * Creates a notification for a system event.
     * This is typically used by event listeners to generate notifications based on system events.
     * 
     * @param type The notification type
     * @param recipientId The ID of the recipient
     * @param subject The notification subject
     * @param content The notification content
     * @param metadata Additional data for the notification
     * @return The created notification response
     */
    NotificationResponse createSystemNotification(
            NotificationType type,
            Long recipientId,
            String subject,
            String content,
            Object... metadata);
    
    /**
     * Sends a notification through a specific channel.
     * This is used internally by the service to delegate to the appropriate channel-specific service.
     * 
     * @param request The notification request
     * @param channel The channel to use for delivery
     * @return The notification response with delivery status
     */
    NotificationResponse sendNotificationViaChannel(NotificationRequest request, NotificationChannel channel);
}
