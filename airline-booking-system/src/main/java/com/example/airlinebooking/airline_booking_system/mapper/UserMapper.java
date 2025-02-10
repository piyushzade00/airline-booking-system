package com.example.airlinebooking.airline_booking_system.mapper;

import com.example.airlinebooking.airline_booking_system.dto.user.UserRequestDTO;
import com.example.airlinebooking.airline_booking_system.dto.user.UserResponseDTO;
import com.example.airlinebooking.airline_booking_system.entity.UserEntity;
import com.example.airlinebooking.airline_booking_system.enums.UserRoles;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserEntity toEntity(UserRequestDTO userRequestDTO) {
        if (userRequestDTO == null) {
            return null;
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName(userRequestDTO.getUserName());
        userEntity.setPassword(userRequestDTO.getPassword());
        userEntity.setEmail(userRequestDTO.getEmail());

        // Set default role if not provided
        userEntity.setRole(userRequestDTO.getRole() != null ? userRequestDTO.getRole() : UserRoles.CUSTOMER);

        return userEntity;
    }

    public UserResponseDTO toResponseDTO(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }
        return new UserResponseDTO(
                userEntity.getUserId(),
                userEntity.getUserName(),
                userEntity.getDisplayName(),
                userEntity.getEmail(),
                userEntity.getCreatedAt()
        );
    }
}
