package com.example.airlinebooking.airline_booking_system.dto.notification;

import com.example.airlinebooking.airline_booking_system.enums.NotificationStatus;
import com.example.airlinebooking.airline_booking_system.enums.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationResponseDTO {

    private Long notificationId;
    private String message;
    private NotificationType notificationType;
    private NotificationStatus status;
    private LocalDateTime createdDate;
    private String userName;
}
