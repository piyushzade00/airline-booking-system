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

    List<NotificationResponseDTO> getNotificationsByUser(String userName);

    List<NotificationResponseDTO> getNotificationsByUserAndStatus(String userName, NotificationStatus status);

    List<NotificationResponseDTO> getNotificationsByUserAndType(String userName, NotificationType type);

    List<NotificationResponseDTO> getNotificationsAfterDate(String userName, LocalDateTime createdDate);

    long countUnreadNotifications(String userName);

    boolean deleteNotificationsBeforeDate(String userName, LocalDateTime createdDate);

    boolean deleteNotificationsByUser(String userName);
}
