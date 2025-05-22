package com.charity.notificationservice.service;

import com.charity.notificationservice.model.Notification;

/**
 * Service interface for email notification delivery.
 * This interface defines the functionality for sending notifications via email.
 * 
 * @author Charity Platform Team
 */
public interface EmailService {
    
    /**
     * Sends a notification via email.
     * The implementation should handle email formatting, addressing, and delivery.
     * 
     * @param notification The notification to send
     * @return true if the email was sent successfully, false otherwise
     */
    boolean sendEmail(Notification notification);
    
    /**
     * Sends a simple email with the specified parameters.
     * This is a utility method for sending emails without creating a full notification entity.
     * 
     * @param to The recipient email address
     * @param subject The email subject
     * @param content The email content (may include HTML)
     * @return true if the email was sent successfully, false otherwise
     */
    boolean sendSimpleEmail(String to, String subject, String content);
}
