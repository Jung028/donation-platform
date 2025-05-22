package com.charity.notificationservice.model;

/**
 * Enumeration of notification delivery channels.
 * Each channel represents a different method of delivering notifications to users.
 * Users can configure their preferences to receive notifications through specific channels
 * for different notification types.
 * 
 * <p>The channel affects how the notification content is formatted and delivered,
 * with each channel having different constraints and capabilities.</p>
 * 
 * @author Charity Platform Team
 */
public enum NotificationChannel {
    /**
     * Notifications delivered via email.
     * Email notifications can contain rich HTML content and are suitable for detailed information.
     * They have the advantage of being accessible even when the user is not actively using the application.
     */
    EMAIL,
    
    /**
     * Notifications delivered via SMS text messages.
     * SMS notifications are brief, plain text, and have character limitations.
     * They are useful for urgent notifications that require immediate attention.
     */
    SMS,
    
    /**
     * Notifications delivered within the application UI.
     * In-app notifications are displayed in a notification center or as toasts/alerts
     * while the user is actively using the application.
     * They support rich content and can include action buttons.
     */
    IN_APP,
    
    /**
     * Notifications delivered as mobile push notifications.
     * Push notifications appear on mobile devices even when the app is not open.
     * They are useful for time-sensitive information.
     */
    PUSH,
    
    /**
     * Notifications delivered through multiple channels simultaneously.
     * Used for critical notifications that must reach the user.
     */
    ALL
}
