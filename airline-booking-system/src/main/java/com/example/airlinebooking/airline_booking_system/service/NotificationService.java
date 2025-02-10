package com.example.airlinebooking.airline_booking_system.service;

import com.example.airlinebooking.airline_booking_system.dto.notification.NotificationRequestDTO;
import com.example.airlinebooking.airline_booking_system.dto.notification.NotificationResponseDTO;
import com.example.airlinebooking.airline_booking_system.entity.UserEntity;
import com.example.airlinebooking.airline_booking_system.enums.NotificationStatus;
import com.example.airlinebooking.airline_booking_system.enums.NotificationType;

import java.time.LocalDateTime;
import java.util.List;

public interface NotificationService {

    NotificationResponseDTO createNotification(NotificationRequestDTO notificationRequestDTO);

    List<NotificationResponseDTO> getNotificationsByUser(NotificationRequestDTO notificationRequestDTO);

    List<NotificationResponseDTO> getNotificationsByUserAndStatus(NotificationRequestDTO notificationRequestDTO, NotificationStatus status);

    List<NotificationResponseDTO> getNotificationsByType(NotificationRequestDTO notificationRequestDTO);

    List<NotificationResponseDTO> getNotificationsAfterDate(NotificationRequestDTO notificationRequestDTO, LocalDateTime createdDate);

    long countUnreadNotifications(NotificationRequestDTO notificationRequestDTO);

    void deleteNotificationsBeforeDate(NotificationRequestDTO notificationRequestDTO, LocalDateTime createdDate);

    void deleteNotificationsByUser(NotificationRequestDTO notificationRequestDTO);
}
