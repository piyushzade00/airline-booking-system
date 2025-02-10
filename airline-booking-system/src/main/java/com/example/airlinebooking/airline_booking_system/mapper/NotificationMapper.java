package com.example.airlinebooking.airline_booking_system.mapper;

import com.example.airlinebooking.airline_booking_system.dto.notification.NotificationRequestDTO;
import com.example.airlinebooking.airline_booking_system.dto.notification.NotificationResponseDTO;
import com.example.airlinebooking.airline_booking_system.entity.NotificationEntity;
import com.example.airlinebooking.airline_booking_system.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {

    public NotificationEntity toEntity(NotificationRequestDTO requestDTO, UserEntity userEntity) {
        if (requestDTO == null || userEntity == null) {
            return null;
        }
        NotificationEntity notificationEntity = new NotificationEntity();
        notificationEntity.setMessage(requestDTO.getMessage());
        notificationEntity.setNotificationType(requestDTO.getNotificationType());
        notificationEntity.setCreatedDate(java.time.LocalDateTime.now());
        notificationEntity.setUser(userEntity);
        return notificationEntity;
    }

    public NotificationResponseDTO toResponseDTO(NotificationEntity notificationEntity) {
        if (notificationEntity == null) {
            return null;
        }
        return new NotificationResponseDTO(
                notificationEntity.getNotificationId(),
                notificationEntity.getMessage(),
                notificationEntity.getNotificationType(),
                notificationEntity.getStatus(),
                notificationEntity.getCreatedDate(),
                notificationEntity.getUser().getUserName()
        );
    }
}