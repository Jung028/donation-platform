package com.charity.notificationservice.service;

import com.charity.notificationservice.model.Notification;

/**
 * Service interface for SMS notification delivery.
 * This interface defines the functionality for sending notifications via SMS text messages.
 * 
 * @author Charity Platform Team
 */
public interface SmsService {
    
    /**
     * Sends a notification via SMS.
     * The implementation should handle SMS formatting, addressing, and delivery.
     * 
     * @param notification The notification to send
     * @return true if the SMS was sent successfully, false otherwise
     */
    boolean sendSms(Notification notification);
    
    /**
     * Sends a simple SMS with the specified parameters.
     * This is a utility method for sending SMS without creating a full notification entity.
     * 
     * @param phoneNumber The recipient phone number
     * @param message The SMS message content
     * @return true if the SMS was sent successfully, false otherwise
     */
    boolean sendSimpleSms(String phoneNumber, String message);
}
