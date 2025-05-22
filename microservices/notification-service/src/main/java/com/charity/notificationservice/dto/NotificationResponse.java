package com.charity.notificationservice.dto;

import com.charity.notificationservice.model.NotificationChannel;
import com.charity.notificationservice.model.NotificationStatus;
import com.charity.notificationservice.model.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Data Transfer Object (DTO) for notification responses.
 * This class represents a notification in API responses, containing
 * all relevant details about the notification and its delivery status.
 * 
 * @author Charity Platform Team
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponse {
    
    /**
     * The unique identifier for the notification.
     */
    private String id;
    
    /**
     * The type of notification.
     */
    private NotificationType type;
    
    /**
     * The channel through which the notification was delivered.
     */
    private NotificationChannel channel;
    
    /**
     * The ID of the user who received the notification.
     */
    private Long recipientId;
    
    /**
     * The subject or title of the notification.
     */
    private String subject;
    
    /**
     * The detailed content of the notification.
     */
    private String content;
    
    /**
     * Current status of the notification.
     */
    private NotificationStatus status;
    
    /**
     * Additional contextual data for the notification.
     */
    private Map<String, Object> metadata;
    
    /**
     * URL associated with the notification.
     */
    private String actionUrl;
    
    /**
     * The timestamp when the notification was created.
     */
    private LocalDateTime createdAt;
    
    /**
     * The timestamp when the notification was sent.
     */
    private LocalDateTime sentAt;
    
    /**
     * The timestamp when the notification was delivered.
     */
    private LocalDateTime deliveredAt;
    
    /**
     * The timestamp when the notification was read.
     */
    private LocalDateTime readAt;
    
    /**
     * Error message if notification delivery failed.
     */
    private String errorMessage;
    
    /**
     * Indicates whether the notification has been read.
     * 
     * @return true if the notification has been read, false otherwise
     */
    public boolean isRead() {
        return readAt != null;
    }
}
