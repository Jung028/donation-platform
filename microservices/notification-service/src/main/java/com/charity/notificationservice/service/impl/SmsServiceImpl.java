package com.charity.notificationservice.service.impl;

import com.charity.notificationservice.model.Notification;
import com.charity.notificationservice.service.SmsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Implementation of the SmsService interface.
 * This class is responsible for sending notifications via SMS text messages.
 * 
 * <p>Note: This is a simplified implementation for demonstration purposes.
 * In a production environment, this would integrate with an actual SMS service
 * provider like Twilio, Nexmo, or AWS SNS.</p>
 * 
 * @author Charity Platform Team
 */
@Service
@Slf4j
public class SmsServiceImpl implements SmsService {
    
    @Override
    public boolean sendSms(Notification notification) {
        String recipientPhone = notification.getRecipientPhone();
        
        // If recipient phone is not provided in the notification, we would
        // typically look it up from a user service based on recipientId
        if (recipientPhone == null || recipientPhone.isEmpty()) {
            log.warn("No recipient phone number provided for notification ID: {}", notification.getId());
            return false;
        }
        
        try {
            // Format SMS content - typically this would be a shorter version of the notification content
            String smsContent = formatSmsContent(notification.getSubject(), notification.getContent());
            
            log.info("Sending SMS notification to: {}", recipientPhone);
            log.info("Content: {}", smsContent);
            
            // In a real implementation, this would use an SMS gateway or service
            // like Twilio, Nexmo, or AWS SNS to actually send the SMS
            
            // For demonstration, we'll just log it and pretend it was sent
            log.info("SMS sent successfully (simulated)");
            return true;
        } catch (Exception e) {
            log.error("Failed to send SMS notification: {}", e.getMessage(), e);
            return false;
        }
    }
    
    @Override
    public boolean sendSimpleSms(String phoneNumber, String message) {
        try {
            log.info("Sending simple SMS to: {}", phoneNumber);
            log.info("Message: {}", message);
            
            // In a real implementation, this would use an SMS gateway or service
            
            // For demonstration, we'll just log it and pretend it was sent
            log.info("Simple SMS sent successfully (simulated)");
            return true;
        } catch (Exception e) {
            log.error("Failed to send simple SMS: {}", e.getMessage(), e);
            return false;
        }
    }
    
    /**
     * Formats notification content for SMS delivery.
     * SMS messages have length limitations, so this method ensures the content
     * fits within those limitations.
     * 
     * @param subject The notification subject
     * @param content The notification content
     * @return Formatted SMS content
     */
    private String formatSmsContent(String subject, String content) {
        // Combine subject and content, but ensure it's not too long for SMS
        // Standard SMS length is around 160 characters
        final int MAX_SMS_LENGTH = 160;
        
        String combinedContent = subject + ": " + content;
        
        // Truncate if necessary and add ellipsis
        if (combinedContent.length() > MAX_SMS_LENGTH - 3) {
            return combinedContent.substring(0, MAX_SMS_LENGTH - 3) + "...";
        }
        
        return combinedContent;
    }
}
