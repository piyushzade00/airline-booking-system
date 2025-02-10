package com.example.airlinebooking.airline_booking_system.dto.notification;

import com.example.airlinebooking.airline_booking_system.enums.NotificationStatus;
import com.example.airlinebooking.airline_booking_system.enums.NotificationType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationRequestDTO {

    @NotNull(message = "Message is required.")
    private String message;

    @NotNull(message = "Notification type is required.")
    private NotificationType notificationType;

    @NotNull(message = "Username is required.")
    private String userName;
}
