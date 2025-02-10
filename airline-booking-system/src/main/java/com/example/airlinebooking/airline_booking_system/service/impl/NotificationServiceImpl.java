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
    public List<NotificationResponseDTO> getNotificationsByUser(NotificationRequestDTO notificationRequestDTO) {
        String userName = notificationRequestDTO.getUserName();
        UserEntity userEntity = validateAndGetUserEntity(userName);

        return notificationRepository.findByUser(userEntity)
                .stream()
                .map(notificationMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<NotificationResponseDTO> getNotificationsByUserAndStatus(NotificationRequestDTO notificationRequestDTO, NotificationStatus status) {
        if(status == null) {
            throw new IllegalArgumentException("Notification status cannot be null.");
        }

        String userName = notificationRequestDTO.getUserName();
        UserEntity userEntity = validateAndGetUserEntity(userName);

        return notificationRepository.findByUserAndStatus(userEntity, status)
                .stream()
                .map(notificationMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<NotificationResponseDTO> getNotificationsByType(NotificationRequestDTO notificationRequestDTO) {
        NotificationType type = notificationRequestDTO.getNotificationType();
        if(type == null) {
            throw new IllegalArgumentException("Notification type cannot be null.");
        }

        String userName = notificationRequestDTO.getUserName();
        UserEntity userEntity = validateAndGetUserEntity(userName);

        return notificationRepository.findByUserAndNotificationType(userEntity, type)
                .stream()
                .map(notificationMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<NotificationResponseDTO> getNotificationsAfterDate(NotificationRequestDTO notificationRequestDTO, LocalDateTime createdDate) {
        if(createdDate == null) {
            throw new IllegalArgumentException("Notification createdDate cannot be null.");
        }

        String userName = notificationRequestDTO.getUserName();
        UserEntity userEntity = validateAndGetUserEntity(userName);

        return notificationRepository.findByUserAndCreatedDateAfter(userEntity, createdDate)
                .stream()
                .map(notificationMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public long countUnreadNotifications(NotificationRequestDTO notificationRequestDTO) {
        String userName = notificationRequestDTO.getUserName();
        UserEntity userEntity = validateAndGetUserEntity(userName);
        return notificationRepository.countByUserAndStatus(userEntity, NotificationStatus.UNREAD);
    }

    @Override
    public void deleteNotificationsBeforeDate(NotificationRequestDTO notificationRequestDTO, LocalDateTime createdDate) {
        if(createdDate == null) {
            throw new IllegalArgumentException("Notification createdDate cannot be null.");
        }

        String userName = notificationRequestDTO.getUserName();
        UserEntity userEntity = validateAndGetUserEntity(userName);

        notificationRepository.deleteByUserAndCreatedDateBefore(userEntity, createdDate);
    }

    @Override
    public void deleteNotificationsByUser(NotificationRequestDTO notificationRequestDTO) {
        String userName = notificationRequestDTO.getUserName();
        UserEntity userEntity = validateAndGetUserEntity(userName);

        notificationRepository.deleteByUser(userEntity);
    }

    public UserEntity validateAndGetUserEntity(String userName) {
        if(userName == null || userName.trim().isEmpty()) {
            throw new ResourceNotFoundException("Username is empty");
        }

        return userRepository.findByUserName(userName)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userName));
    }
}

