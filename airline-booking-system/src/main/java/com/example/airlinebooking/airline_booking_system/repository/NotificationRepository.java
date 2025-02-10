package com.example.airlinebooking.airline_booking_system.repository;

import com.example.airlinebooking.airline_booking_system.entity.NotificationEntity;
import com.example.airlinebooking.airline_booking_system.entity.UserEntity;
import com.example.airlinebooking.airline_booking_system.enums.NotificationStatus;
import com.example.airlinebooking.airline_booking_system.enums.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {

    NotificationEntity saveNotification(NotificationEntity notificationEntity);

    // Find all notifications for a specific user
    List<NotificationEntity> findByUser(UserEntity user);

    // Find notifications for a user with a specific status
    List<NotificationEntity> findByUserAndStatus(UserEntity user, NotificationStatus status);

    // Find notifications by type and user
    List<NotificationEntity> findByUserAndNotificationType(UserEntity user, NotificationType type);

    // Get notifications created after a certain date for a user
    List<NotificationEntity> findByUserAndCreatedDateAfter(UserEntity user, LocalDateTime createdDate);

    // Count unread notifications for a user
    long countByUserAndStatus(UserEntity user, NotificationStatus status);

    // Delete notifications for a user before a specific date
    void deleteByUserAndCreatedDateBefore(UserEntity user, LocalDateTime createdDate);

    void deleteByUser(UserEntity user);
}
