package com.charity.notificationservice.service.impl;

import com.charity.notificationservice.dto.NotificationBulkRequest;
import com.charity.notificationservice.dto.NotificationRequest;
import com.charity.notificationservice.dto.NotificationResponse;
import com.charity.notificationservice.model.Notification;
import com.charity.notificationservice.model.NotificationChannel;
import com.charity.notificationservice.model.NotificationStatus;
import com.charity.notificationservice.model.NotificationType;
import com.charity.notificationservice.service.EmailService;
import com.charity.notificationservice.service.NotificationService;
import com.charity.notificationservice.service.SmsService;
import com.charity.notificationservice.service.WebSocketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Implementation of the NotificationService interface.
 * This class manages notification creation, delivery, and lifecycle management.
 * It coordinates with channel-specific services to deliver notifications through
 * various channels like email, SMS, and WebSocket.
 * 
 * @author Charity Platform Team
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {
    
    // In a production environment, these would be stored in a database
    // For simplicity, we're using an in-memory store
    private final Map<String, Notification> notificationStore = new ConcurrentHashMap<>();
    private final Map<Long, List<String>> userNotifications = new ConcurrentHashMap<>();
    
    private final EmailService emailService;
    private final SmsService smsService;
    private final WebSocketService webSocketService;
    
    @Override
    public NotificationResponse sendNotification(NotificationRequest request) {
        log.info("Processing notification request: {}", request);
        
        // Create notification entity
        Notification notification = createNotificationEntity(request);
        
        // Store notification
        storeNotification(notification);
        
        // Determine channel and send
        NotificationChannel channel = request.getChannel();
        if (channel == null) {
            // If no channel specified, determine based on notification type and user preferences
            // For simplicity, default to IN_APP if not specified
            channel = NotificationChannel.IN_APP;
        }
        
        // Send through appropriate channel
        try {
            sendThroughChannel(notification, channel);
            notification.setStatus(NotificationStatus.SENT);
            notification.setSentAt(LocalDateTime.now());
        } catch (Exception e) {
            log.error("Failed to send notification: {}", e.getMessage(), e);
            notification.setStatus(NotificationStatus.FAILED);
            notification.setErrorMessage(e.getMessage());
        }
        
        // Update notification in store
        notificationStore.put(notification.getId(), notification);
        
        return mapToResponse(notification);
    }
    
    @Override
    public List<NotificationResponse> sendBulkNotifications(NotificationBulkRequest request) {
        log.info("Processing bulk notification request for {} recipients", request.getRecipientIds().size());
        
        NotificationRequest baseRequest = request.getNotification();
        return request.getRecipientIds().stream()
                .map(recipientId -> {
                    NotificationRequest individualRequest = new NotificationRequest();
                    
                    // Copy all properties from the base request
                    individualRequest.setType(baseRequest.getType());
                    individualRequest.setSubject(baseRequest.getSubject());
                    individualRequest.setContent(baseRequest.getContent());
                    individualRequest.setChannel(baseRequest.getChannel());
                    individualRequest.setRecipientEmail(baseRequest.getRecipientEmail());
                    individualRequest.setRecipientPhone(baseRequest.getRecipientPhone());
                    individualRequest.setMetadata(baseRequest.getMetadata() != null 
                            ? new HashMap<>(baseRequest.getMetadata()) : new HashMap<>());
                    individualRequest.setActionUrl(baseRequest.getActionUrl());
                    
                    // Set recipient ID for this specific notification
                    individualRequest.setRecipientId(recipientId);
                    
                    // Send individual notification
                    return sendNotification(individualRequest);
                })
                .collect(Collectors.toList());
    }
    
    @Override
    public List<NotificationResponse> getNotificationsForUser(Long userId) {
        log.info("Retrieving all notifications for user ID: {}", userId);
        
        List<String> notificationIds = userNotifications.getOrDefault(userId, Collections.emptyList());
        return notificationIds.stream()
                .map(notificationStore::get)
                .filter(Objects::nonNull)
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<NotificationResponse> getUnreadNotificationsForUser(Long userId) {
        log.info("Retrieving unread notifications for user ID: {}", userId);
        
        List<String> notificationIds = userNotifications.getOrDefault(userId, Collections.emptyList());
        return notificationIds.stream()
                .map(notificationStore::get)
                .filter(Objects::nonNull)
                .filter(n -> n.getReadAt() == null)
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    public NotificationResponse markAsRead(String notificationId) {
        log.info("Marking notification as read: {}", notificationId);
        
        Notification notification = notificationStore.get(notificationId);
        if (notification != null && notification.getReadAt() == null) {
            notification.setReadAt(LocalDateTime.now());
            notification.setStatus(NotificationStatus.READ);
            notification.setUpdatedAt(LocalDateTime.now());
            notificationStore.put(notificationId, notification);
        }
        
        return notification != null ? mapToResponse(notification) : null;
    }
    
    @Override
    public int markAllAsRead(Long userId) {
        log.info("Marking all notifications as read for user ID: {}", userId);
        
        List<String> notificationIds = userNotifications.getOrDefault(userId, Collections.emptyList());
        int count = 0;
        
        for (String id : notificationIds) {
            Notification notification = notificationStore.get(id);
            if (notification != null && notification.getReadAt() == null) {
                notification.setReadAt(LocalDateTime.now());
                notification.setStatus(NotificationStatus.READ);
                notification.setUpdatedAt(LocalDateTime.now());
                notificationStore.put(id, notification);
                count++;
            }
        }
        
        return count;
    }
    
    @Override
    public NotificationResponse createSystemNotification(
            NotificationType type, 
            Long recipientId, 
            String subject, 
            String content, 
            Object... metadata) {
        
        NotificationRequest request = new NotificationRequest();
        request.setType(type);
        request.setRecipientId(recipientId);
        request.setSubject(subject);
        request.setContent(content);
        
        // Add metadata as key-value pairs
        if (metadata != null && metadata.length > 0) {
            Map<String, Object> metadataMap = new HashMap<>();
            for (int i = 0; i < metadata.length; i += 2) {
                if (i + 1 < metadata.length) {
                    metadataMap.put(metadata[i].toString(), metadata[i + 1]);
                }
            }
            request.setMetadata(metadataMap);
        }
        
        return sendNotification(request);
    }
    
    @Override
    public NotificationResponse sendNotificationViaChannel(NotificationRequest request, NotificationChannel channel) {
        // Set the specified channel
        request.setChannel(channel);
        return sendNotification(request);
    }
    
    /**
     * Creates a new notification entity from a request.
     * 
     * @param request The notification request
     * @return The created notification entity
     */
    private Notification createNotificationEntity(NotificationRequest request) {
        String id = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now();
        
        return Notification.builder()
                .id(id)
                .type(request.getType())
                .channel(request.getChannel())
                .recipientId(request.getRecipientId())
                .recipientEmail(request.getRecipientEmail())
                .recipientPhone(request.getRecipientPhone())
                .subject(request.getSubject())
                .content(request.getContent())
                .status(NotificationStatus.PENDING)
                .metadata(request.getMetadata())
                .actionUrl(request.getActionUrl())
                .createdAt(now)
                .updatedAt(now)
                .build();
    }
    
    /**
     * Stores a notification in the in-memory store and links it to the recipient user.
     * 
     * @param notification The notification to store
     */
    private void storeNotification(Notification notification) {
        notificationStore.put(notification.getId(), notification);
        
        // Add to user's notifications list
        userNotifications.computeIfAbsent(notification.getRecipientId(), k -> new ArrayList<>())
                .add(notification.getId());
    }
    
    /**
     * Sends a notification through the specified channel.
     * 
     * @param notification The notification to send
     * @param channel The channel to use
     */
    private void sendThroughChannel(Notification notification, NotificationChannel channel) {
        switch (channel) {
            case EMAIL:
                emailService.sendEmail(notification);
                break;
            case SMS:
                smsService.sendSms(notification);
                break;
            case IN_APP:
                webSocketService.sendNotification(notification);
                break;
            case PUSH:
                // Push notification service would be called here
                log.info("Push notifications not yet implemented");
                break;
            case ALL:
                // Send through all available channels
                emailService.sendEmail(notification);
                smsService.sendSms(notification);
                webSocketService.sendNotification(notification);
                break;
            default:
                log.warn("Unknown notification channel: {}", channel);
                break;
        }
    }
    
    /**
     * Maps a notification entity to a response DTO.
     * 
     * @param notification The notification entity
     * @return The notification response DTO
     */
    private NotificationResponse mapToResponse(Notification notification) {
        return NotificationResponse.builder()
                .id(notification.getId())
                .type(notification.getType())
                .channel(notification.getChannel())
                .recipientId(notification.getRecipientId())
                .subject(notification.getSubject())
                .content(notification.getContent())
                .status(notification.getStatus())
                .metadata(notification.getMetadata())
                .actionUrl(notification.getActionUrl())
                .createdAt(notification.getCreatedAt())
                .sentAt(notification.getSentAt())
                .deliveredAt(notification.getDeliveredAt())
                .readAt(notification.getReadAt())
                .errorMessage(notification.getErrorMessage())
                .build();
    }
}
