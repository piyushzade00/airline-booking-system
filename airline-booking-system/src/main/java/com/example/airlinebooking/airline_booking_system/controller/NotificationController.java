package com.example.airlinebooking.airline_booking_system.controller;

import com.example.airlinebooking.airline_booking_system.dto.notification.NotificationRequestDTO;
import com.example.airlinebooking.airline_booking_system.dto.notification.NotificationResponseDTO;
import com.example.airlinebooking.airline_booking_system.enums.NotificationStatus;
import com.example.airlinebooking.airline_booking_system.enums.NotificationType;
import com.example.airlinebooking.airline_booking_system.service.NotificationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create-notification")
    public ResponseEntity<NotificationResponseDTO> createNotification(
            @Valid @RequestBody NotificationRequestDTO notificationRequestDTO) {
        NotificationResponseDTO response = notificationService.createNotification(notificationRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'AGENT')")
    @GetMapping("/get-notification-by-username/{userName}")
    public ResponseEntity<List<NotificationResponseDTO>> getNotificationsByUser(
            @PathVariable String userName) {
        List<NotificationResponseDTO> notifications = notificationService.getNotificationsByUser(userName);
        return ResponseEntity.ok(notifications);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'AGENT')")
    @GetMapping("/get-notifications-by-user-and-status")
    public ResponseEntity<List<NotificationResponseDTO>> getNotificationsByUserAndStatus(
            @RequestParam String userName, @RequestParam NotificationStatus status) {
        List<NotificationResponseDTO> notifications = notificationService.getNotificationsByUserAndStatus(userName, status);
        return ResponseEntity.ok(notifications);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'AGENT')")
    @GetMapping("/get-notifications-by-user-and-type")
    public ResponseEntity<List<NotificationResponseDTO>> getNotificationsByUserAndType(
            @RequestParam String userName, @RequestParam NotificationType type) {
        List<NotificationResponseDTO> notifications = notificationService.getNotificationsByUserAndType(userName, type);
        return ResponseEntity.ok(notifications);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'AGENT')")
    @GetMapping("/get-notifications-after-date")
    public ResponseEntity<List<NotificationResponseDTO>> getNotificationsAfterDate(
            @RequestParam String userName, @RequestParam String createdDate) {
        LocalDateTime date = LocalDateTime.parse(createdDate);
        List<NotificationResponseDTO> notifications = notificationService.getNotificationsAfterDate(userName, date);
        return ResponseEntity.ok(notifications);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'AGENT')")
    @GetMapping("/count-unread-notifications/{userName}")
    public ResponseEntity<Long> countUnreadNotifications(@PathVariable String userName) {
        long unreadCount = notificationService.countUnreadNotifications(userName);
        return ResponseEntity.ok(unreadCount);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete-notifications-before-date")
    public ResponseEntity<Boolean> deleteNotificationsBeforeDate(
            @RequestParam String userName, @RequestParam String createdDate) {
        LocalDateTime date = LocalDateTime.parse(createdDate);
        boolean isDeleted = notificationService.deleteNotificationsBeforeDate(userName, date);
        return ResponseEntity.ok(isDeleted);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete-notifications-by-user")
    public ResponseEntity<Boolean> deleteNotificationsByUser(@PathVariable String userName) {
        boolean isDeleted = notificationService.deleteNotificationsByUser(userName);
        return ResponseEntity.ok(isDeleted);
    }
}

