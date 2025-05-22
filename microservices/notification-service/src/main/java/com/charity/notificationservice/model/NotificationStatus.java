package com.charity.notificationservice.model;

/**
 * Enumeration of possible notification states throughout the notification lifecycle.
 * These statuses track the progress of a notification from creation through delivery
 * and user interaction.
 * 
 * <p>The status progression typically follows:
 * PENDING → SENT → DELIVERED → READ
 * or alternatively can result in FAILED at any point in the process.</p>
 * 
 * @author Charity Platform Team
 */
public enum NotificationStatus {
    /**
     * The notification has been created but not yet processed for delivery.
     * This is the initial state of a new notification.
     */
    PENDING,
    
    /**
     * The notification has been processed and sent to the delivery system
     * (email server, SMS gateway, or WebSocket handler).
     * At this stage, it has left the notification service but may not yet
     * have reached the recipient.
     */
    SENT,
    
    /**
     * The notification has been successfully delivered to the recipient.
     * For email and SMS, this means the delivery system has confirmed receipt.
     * For in-app notifications, this means it has been delivered to the user's
     * notification feed.
     */
    DELIVERED,
    
    /**
     * The notification has been viewed or read by the recipient.
     * This status is typically only tracked for in-app notifications.
     */
    READ,
    
    /**
     * The notification delivery has failed.
     * This could be due to invalid contact information, delivery system errors,
     * or other technical issues.
     */
    FAILED,
    
    /**
     * The notification has been deleted by the recipient.
     * This applies primarily to in-app notifications.
     */
    DELETED
}
