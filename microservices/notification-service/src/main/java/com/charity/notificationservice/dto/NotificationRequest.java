package com.charity.notificationservice.dto;

import com.charity.notificationservice.model.NotificationChannel;
import com.charity.notificationservice.model.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

/**
 * Data Transfer Object (DTO) for creating a new notification.
 * Contains all the information needed to generate and send a notification to a recipient.
 * This class is used in API requests to the notification service.
 * 
 * @author Charity Platform Team
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequest {
    
    /**
     * The type of notification to send.
     * This determines the template and content structure.
     */
    @NotNull(message = "Notification type is required")
    private NotificationType type;
    
    /**
     * The ID of the user who should receive the notification.
     */
    @NotNull(message = "Recipient ID is required")
    private Long recipientId;
    
    /**
     * The subject or title of the notification.
     * This is typically displayed as a heading or preview.
     */
    @NotBlank(message = "Subject is required")
    private String subject;
    
    /**
     * The detailed content or body of the notification.
     * This may include HTML formatting for certain channels.
     */
    @NotBlank(message = "Content is required")
    private String content;
    
    /**
     * The channel through which to deliver the notification.
     * If null, the recipient's preferred channel for this notification type will be used.
     */
    private NotificationChannel channel;
    
    /**
     * Optional email address override for the recipient.
     * If provided, this will be used instead of looking up the recipient's email.
     */
    private String recipientEmail;
    
    /**
     * Optional phone number override for the recipient.
     * If provided, this will be used instead of looking up the recipient's phone number.
     */
    private String recipientPhone;
    
    /**
     * Additional contextual data for the notification.
     * This can be used to populate templates or provide extra information.
     */
    private Map<String, Object> metadata = new HashMap<>();
    
    /**
     * Optional URL that the notification should link to.
     * This can be used to direct users to a relevant page when they interact with the notification.
     */
    private String actionUrl;
    
    /**
     * Convenience method to add metadata to the request.
     * 
     * @param key The metadata key
     * @param value The metadata value
     * @return This request instance for method chaining
     */
    public NotificationRequest addMetadata(String key, Object value) {
        if (this.metadata == null) {
            this.metadata = new HashMap<>();
        }
        this.metadata.put(key, value);
        return this;
    }
}
