package com.charity.notificationservice.model;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a notification entity in the system.
 * This class models a notification that can be sent to users through various channels.
 * It contains all the information needed to deliver and track a notification.
 * 
 * <p>Notifications can be personalized with dynamic content and metadata,
 * and their delivery status is tracked throughout the lifecycle.</p>
 * 
 * @author Charity Platform Team
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    
    /**
     * The unique identifier for the notification.
     */
    private String id;
    
    /**
     * The type of notification, determining its purpose and template.
     * @see NotificationType
     */
    private NotificationType type;
    
    /**
     * The delivery channel for this notification.
     * @see NotificationChannel
     */
    private NotificationChannel channel;
    
    /**
     * The ID of the user receiving the notification.
     */
    private Long recipientId;
    
    /**
     * The email address for email notifications.
     * Only relevant when channel is EMAIL.
     */
    private String recipientEmail;
    
    /**
     * The phone number for SMS notifications.
     * Only relevant when channel is SMS.
     */
    private String recipientPhone;
    
    /**
     * Brief summary or subject line of the notification.
     */
    private String subject;
    
    /**
     * Detailed notification message content.
     * May contain HTML for email notifications.
     */
    private String content;
    
    /**
     * Current status of the notification in its lifecycle.
     * @see NotificationStatus
     */
    private NotificationStatus status;
    
    /**
     * Additional contextual data for the notification.
     * This can include data needed for templates or tracking information.
     */
    private Map<String, Object> metadata = new HashMap<>();
    
    /**
     * Link or action URL associated with the notification.
     * When clicked, this directs the user to a relevant page or action.
     */
    private String actionUrl;
    
    /**
     * The timestamp when the notification was created.
     */
    private LocalDateTime createdAt;
    
    /**
     * The timestamp when the notification was last updated.
     */
    private LocalDateTime updatedAt;
    
    /**
     * The timestamp when the notification was sent.
     * Null if the notification has not been sent yet.
     */
    private LocalDateTime sentAt;
    
    /**
     * The timestamp when the notification was delivered.
     * Null if the notification has not been delivered yet.
     */
    private LocalDateTime deliveredAt;
    
    /**
     * The timestamp when the notification was read by the recipient.
     * Null if the notification has not been read yet.
     */
    private LocalDateTime readAt;
    
    /**
     * Error message if notification delivery failed.
     * Null if no errors occurred.
     */
    private String errorMessage;
    
    /**
     * Adds metadata to the notification.
     * 
     * @param key The metadata key
     * @param value The metadata value
     * @return This notification instance for method chaining
     */
    public Notification addMetadata(String key, Object value) {
        if (this.metadata == null) {
            this.metadata = new HashMap<>();
        }
        this.metadata.put(key, value);
        return this;
    }
}
