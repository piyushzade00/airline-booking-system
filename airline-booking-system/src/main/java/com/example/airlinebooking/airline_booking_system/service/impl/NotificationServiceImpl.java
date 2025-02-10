package com.example.airlinebooking.airline_booking_system.service.impl;

import com.example.airlinebooking.airline_booking_system.dto.notification.NotificationRequestDTO;
import com.example.airlinebooking.airline_booking_system.dto.notification.NotificationResponseDTO;
import com.example.airlinebooking.airline_booking_system.entity.NotificationEntity;
import com.example.airlinebooking.airline_booking_system.entity.UserEntity;
import com.example.airlinebooking.airline_booking_system.enums.NotificationStatus;
import com.example.airlinebooking.airline_booking_system.enums.NotificationType;
import com.example.airlinebooking.airline_booking_system.exception.ResourceNotFoundException;
import com.example.airlinebooking.airline_booking_system.mapper.NotificationMapper;
import com.example.airlinebooking.airline_booking_system.repository.NotificationRepository;
import com.example.airlinebooking.airline_booking_system.repository.UserRepository;
import com.example.airlinebooking.airline_booking_system.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final NotificationMapper notificationMapper;

    @Autowired
    public NotificationServiceImpl(NotificationRepository notificationRepository, UserRepository userRepository, NotificationMapper notificationMapper) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
        this.notificationMapper = notificationMapper;
    }

    @Override
    public NotificationResponseDTO createNotification(NotificationRequestDTO notificationRequestDTO) {
        String userName = notificationRequestDTO.getUserName();

        UserEntity userEntity = validateAndGetUserEntity(userName);

        NotificationEntity notificationEntity = notificationMapper.toEntity(notificationRequestDTO, userEntity);
        NotificationEntity savedNotification = notificationRepository.saveNotification(notificationEntity);
        return notificationMapper.toResponseDTO(savedNotification);
    }

    @Override
    public List<NotificationResponseDTO> getNotificationsByUser(String userName) {
        UserEntity userEntity = validateAndGetUserEntity(userName);

        return notificationRepository.findByUser(userEntity)
                .stream()
                .map(notificationMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<NotificationResponseDTO> getNotificationsByUserAndStatus(String userName, NotificationStatus status) {
        if(status == null) {
            throw new IllegalArgumentException("Notification status cannot be null.");
        }

        UserEntity userEntity = validateAndGetUserEntity(userName);

        return notificationRepository.findByUserAndStatus(userEntity, status)
                .stream()
                .map(notificationMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<NotificationResponseDTO> getNotificationsByUserAndType(String userName, NotificationType type) {
        if(type == null) {
            throw new IllegalArgumentException("Notification type cannot be null.");
        }

        UserEntity userEntity = validateAndGetUserEntity(userName);

        return notificationRepository.findByUserAndNotificationType(userEntity, type)
                .stream()
                .map(notificationMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<NotificationResponseDTO> getNotificationsAfterDate(String userName, LocalDateTime createdDate) {
        if(createdDate == null) {
            throw new IllegalArgumentException("Notification createdDate cannot be null.");
        }

        UserEntity userEntity = validateAndGetUserEntity(userName);

        return notificationRepository.findByUserAndCreatedDateAfter(userEntity, createdDate)
                .stream()
                .map(notificationMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public long countUnreadNotifications(String userName) {
        UserEntity userEntity = validateAndGetUserEntity(userName);
        return notificationRepository.countByUserAndStatus(userEntity, NotificationStatus.UNREAD);
    }

    @Override
    public boolean deleteNotificationsBeforeDate(String userName, LocalDateTime createdDate) {
        if(createdDate == null) {
            throw new IllegalArgumentException("Notification createdDate cannot be null.");
        }

        UserEntity userEntity = validateAndGetUserEntity(userName);

        return notificationRepository.deleteByUserAndCreatedDateBefore(userEntity, createdDate);
    }

    @Override
    public boolean deleteNotificationsByUser(String userName) {
        UserEntity userEntity = validateAndGetUserEntity(userName);

        return notificationRepository.deleteByUser(userEntity);
    }

    public UserEntity validateAndGetUserEntity(String userName) {
        if(userName == null || userName.trim().isEmpty()) {
            throw new ResourceNotFoundException("Username is empty");
        }

        return userRepository.findByUserName(userName)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userName));
    }
}

