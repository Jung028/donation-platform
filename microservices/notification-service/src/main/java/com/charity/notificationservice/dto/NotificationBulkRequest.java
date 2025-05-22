package com.charity.notificationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

/**
 * Data Transfer Object (DTO) for sending notifications to multiple recipients.
 * This class is used in API requests to send the same notification to multiple users at once.
 * 
 * @author Charity Platform Team
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationBulkRequest {
    
    /**
     * List of recipient user IDs to send the notification to.
     * Each user in this list will receive the same notification.
     */
    @NotEmpty(message = "At least one recipient ID is required")
    private List<Long> recipientIds;
    
    /**
     * The notification request containing all details of the notification to send.
     * The recipientId in this object will be ignored, as it's overridden by each ID in recipientIds.
     */
    @NotNull(message = "Notification details are required")
    @Valid
    private NotificationRequest notification;
}
