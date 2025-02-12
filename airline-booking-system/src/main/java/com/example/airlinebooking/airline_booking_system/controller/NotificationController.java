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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "Notification Management", description = "APIs for managing user notifications")
@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Operation(
            summary = "Create a new notification",
            description = "Creates a new notification and stores it in the system. Requires ADMIN role."
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create-notification")
    public ResponseEntity<NotificationResponseDTO> createNotification(
            @Valid @RequestBody NotificationRequestDTO notificationRequestDTO) {
        NotificationResponseDTO response = notificationService.createNotification(notificationRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Get notifications by username",
            description = "Retrieves all notifications associated with a specific username."
    )
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'AGENT')")
    @GetMapping("/get-notification-by-username/{userName}")
    public ResponseEntity<List<NotificationResponseDTO>> getNotificationsByUser(
            @PathVariable String userName) {
        List<NotificationResponseDTO> notifications = notificationService.getNotificationsByUser(userName);
        return ResponseEntity.ok(notifications);
    }

    @Operation(
            summary = "Get notifications by username and status",
            description = "Fetches notifications for a user based on status (e.g., READ, UNREAD)."
    )
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'AGENT')")
    @GetMapping("/get-notifications-by-user-and-status")
    public ResponseEntity<List<NotificationResponseDTO>> getNotificationsByUserAndStatus(
            @RequestParam String userName, @RequestParam NotificationStatus status) {
        List<NotificationResponseDTO> notifications = notificationService.getNotificationsByUserAndStatus(userName, status);
        return ResponseEntity.ok(notifications);
    }

    @Operation(
            summary = "Get notifications by username and type",
            description = "Fetches notifications for a user based on notification type (e.g., EMAIL, SMS, PUSH)."
    )
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'AGENT')")
    @GetMapping("/get-notifications-by-user-and-type")
    public ResponseEntity<List<NotificationResponseDTO>> getNotificationsByUserAndType(
            @RequestParam String userName, @RequestParam NotificationType type) {
        List<NotificationResponseDTO> notifications = notificationService.getNotificationsByUserAndType(userName, type);
        return ResponseEntity.ok(notifications);
    }

    @Operation(
            summary = "Get notifications after a specific date",
            description = "Retrieves notifications for a user created after the specified date."
    )
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'AGENT')")
    @GetMapping("/get-notifications-after-date")
    public ResponseEntity<List<NotificationResponseDTO>> getNotificationsAfterDate(
            @RequestParam String userName, @RequestParam String createdDate) {
        LocalDateTime date = LocalDateTime.parse(createdDate);
        List<NotificationResponseDTO> notifications = notificationService.getNotificationsAfterDate(userName, date);
        return ResponseEntity.ok(notifications);
    }

    @Operation(
            summary = "Count unread notifications",
            description = "Returns the number of unread notifications for a given username."
    )
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'AGENT')")
    @GetMapping("/count-unread-notifications/{userName}")
    public ResponseEntity<Long> countUnreadNotifications(@PathVariable String userName) {
        long unreadCount = notificationService.countUnreadNotifications(userName);
        return ResponseEntity.ok(unreadCount);
    }

    @Operation(
            summary = "Delete notifications before a specific date",
            description = "Deletes all notifications for a user that were created before the specified date. Requires ADMIN role."
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete-notifications-before-date")
    public ResponseEntity<Boolean> deleteNotificationsBeforeDate(
            @RequestParam String userName, @RequestParam String createdDate) {
        LocalDateTime date = LocalDateTime.parse(createdDate);
        boolean isDeleted = notificationService.deleteNotificationsBeforeDate(userName, date);
        return ResponseEntity.ok(isDeleted);
    }

    @Operation(
            summary = "Delete all notifications for a user",
            description = "Deletes all notifications associated with a specific username. Requires ADMIN role."
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete-notifications-by-user/{userName}")
    public ResponseEntity<Boolean> deleteNotificationsByUser(@PathVariable String userName) {
        boolean isDeleted = notificationService.deleteNotificationsByUser(userName);
        return ResponseEntity.ok(isDeleted);
    }
}

