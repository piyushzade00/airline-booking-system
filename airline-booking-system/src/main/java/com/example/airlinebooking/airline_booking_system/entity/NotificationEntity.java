package com.example.airlinebooking.airline_booking_system.entity;
import com.example.airlinebooking.airline_booking_system.enums.NotificationStatus;
import com.example.airlinebooking.airline_booking_system.enums.NotificationType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long notificationId;

    @NotNull(message = "Message cannot be null.")
    @Column(name = "message", nullable = false)
    private String message;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Notification type is required.")
    @Column(name = "notification_type", nullable = false)
    private NotificationType notificationType;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Notification status is required.")
    @Column(name = "status", nullable = false)
    private NotificationStatus status;

    @NotNull(message = "Created date is required.")
    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    // Reference to User (One User can have multiple notifications)
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull(message = "User must be associated with the notification.")
    private UserEntity user;
}