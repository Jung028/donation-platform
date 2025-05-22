package com.charity.notificationservice.controller;

import com.charity.notificationservice.dto.NotificationBulkRequest;
import com.charity.notificationservice.dto.NotificationRequest;
import com.charity.notificationservice.dto.NotificationResponse;
import com.charity.notificationservice.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST controller for notification management.
 * This controller exposes endpoints for creating, retrieving, and managing notifications.
 * 
 * @author Charity Platform Team
 */
@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
@Slf4j
public class NotificationController {
    
    private final NotificationService notificationService;
    
    /**
     * Creates and sends a notification.
     * 
     * @param request The notification request containing all necessary details
     * @return ResponseEntity containing the created notification with HTTP 201 status
     */
    @PostMapping
    public ResponseEntity<NotificationResponse> sendNotification(@Valid @RequestBody NotificationRequest request) {
        log.info("Received request to send notification: {}", request);
        NotificationResponse response = notificationService.sendNotification(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    /**
     * Sends a notification to multiple recipients.
     * 
     * @param request The bulk notification request
     * @return ResponseEntity containing a list of created notifications with HTTP 201 status
     */
    @PostMapping("/bulk")
    public ResponseEntity<List<NotificationResponse>> sendBulkNotifications(
            @Valid @RequestBody NotificationBulkRequest request) {
        log.info("Received request to send bulk notifications to {} recipients", request.getRecipientIds().size());
        List<NotificationResponse> responses = notificationService.sendBulkNotifications(request);
        return new ResponseEntity<>(responses, HttpStatus.CREATED);
    }
    
    /**
     * Retrieves all notifications for a specific user.
     * 
     * @param userId The ID of the user whose notifications to retrieve
     * @return ResponseEntity containing a list of notifications with HTTP 200 status
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NotificationResponse>> getNotificationsForUser(@PathVariable Long userId) {
        log.info("Retrieving notifications for user ID: {}", userId);
        List<NotificationResponse> notifications = notificationService.getNotificationsForUser(userId);
        return ResponseEntity.ok(notifications);
    }
    
    /**
     * Retrieves all unread notifications for a specific user.
     * 
     * @param userId The ID of the user whose unread notifications to retrieve
     * @return ResponseEntity containing a list of unread notifications with HTTP 200 status
     */
    @GetMapping("/user/{userId}/unread")
    public ResponseEntity<List<NotificationResponse>> getUnreadNotificationsForUser(@PathVariable Long userId) {
        log.info("Retrieving unread notifications for user ID: {}", userId);
        List<NotificationResponse> notifications = notificationService.getUnreadNotificationsForUser(userId);
        return ResponseEntity.ok(notifications);
    }
    
    /**
     * Marks a notification as read.
     * 
     * @param id The ID of the notification to mark as read
     * @return ResponseEntity containing the updated notification with HTTP 200 status
     */
    @PatchMapping("/{id}/read")
    public ResponseEntity<NotificationResponse> markAsRead(@PathVariable String id) {
        log.info("Marking notification as read: {}", id);
        NotificationResponse notification = notificationService.markAsRead(id);
        return ResponseEntity.ok(notification);
    }
    
    /**
     * Marks all notifications for a user as read.
     * 
     * @param userId The ID of the user whose notifications to mark as read
     * @return ResponseEntity containing the count of notifications marked as read with HTTP 200 status
     */
    @PatchMapping("/user/{userId}/read-all")
    public ResponseEntity<Map<String, Integer>> markAllAsRead(@PathVariable Long userId) {
        log.info("Marking all notifications as read for user ID: {}", userId);
        int count = notificationService.markAllAsRead(userId);
        return ResponseEntity.ok(Map.of("count", count));
    }
}
