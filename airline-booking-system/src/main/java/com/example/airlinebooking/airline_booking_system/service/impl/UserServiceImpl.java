package com.example.airlinebooking.airline_booking_system.service.impl;

import com.example.airlinebooking.airline_booking_system.dto.user.UserRequestDTO;
import com.example.airlinebooking.airline_booking_system.dto.user.UserResponseDTO;
import com.example.airlinebooking.airline_booking_system.entity.UserEntity;
import com.example.airlinebooking.airline_booking_system.enums.UserRoles;
import com.example.airlinebooking.airline_booking_system.exception.UserNotFoundException;
import com.example.airlinebooking.airline_booking_system.mapper.UserMapper;
import com.example.airlinebooking.airline_booking_system.repository.UserRepository;
import com.example.airlinebooking.airline_booking_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        if(userRequestDTO == null) {
            throw new IllegalArgumentException("User data not present.");
        }

        String userName = userRequestDTO.getUserName();
        if(userName == null || userName.trim().isEmpty()) {
            throw new IllegalArgumentException("Username not present.");
        }
        if (userRepository.existsByUserName(userName)) {
            throw new IllegalArgumentException("Username already exists.");
        }

        String email = userRequestDTO.getEmail();
        if(email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email not present.");
        }
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email is already registered.");
        }

        UserEntity userEntity = userMapper.toEntity(userRequestDTO);
        UserEntity savedUser = userRepository.saveUserEntity(userEntity);
        return userMapper.toResponseDTO(savedUser);
    }

    @Override
    public UserResponseDTO getUserByUsername(String userName) {
        if(userName == null || userName.trim().isEmpty()) {
            throw new IllegalArgumentException("Please provide username.");
        }
        UserEntity userEntity = userRepository.findByUserName(userName)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + userName));
        return userMapper.toResponseDTO(userEntity);
    }

    @Override
    public UserResponseDTO getUserByEmail(String email) {
        if(email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Please provide username.");
        }
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
        return userMapper.toResponseDTO(userEntity);
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAllUsers()
                .stream()
                .map(userMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserResponseDTO> getUsersByRole(UserRoles role) {
        if(role == null) {
            throw new IllegalArgumentException("Please provide a valid user role.");
        }
        if(!isValidEnumValueStream(UserRoles.class,role.toString()))
        {
            throw new IllegalArgumentException("Please provide valid user role.");
        }

        return userRepository.findByRole(role)
                .stream()
                .map(userMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public boolean userExistsByUsername(String userName) {
        if(userName == null || userName.trim().isEmpty()) {
            throw new IllegalArgumentException("Please provide username.");
        }
        return userRepository.existsByUserName(userName);
    }

    @Override
    public boolean userExistsByEmail(String email) {
        if(email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Please provide a valid email.");
        }
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean deleteUserByUsername(String userName) {
        if(userName == null || userName.trim().isEmpty()) {
            throw new IllegalArgumentException("Please provide username.");
        }
        if (!userRepository.existsByUserName(userName)) {
            throw new UserNotFoundException("No user found with username: " + userName);
        }
        return userRepository.deleteByUserName(userName);
    }

    @Override
    public boolean deleteUserByEmail(String email) {
        if(email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Please provide a valid email.");
        }
        if (!userRepository.existsByEmail(email)) {
            throw new UserNotFoundException("No user found with email: " + email);
        }
        return userRepository.deleteByEmail(email);
    }

    public static <T extends Enum<T>> boolean isValidEnumValueStream(Class<T> enumClass, String value) {
        return Arrays.stream(enumClass.getEnumConstants())
                .anyMatch(e -> e.name().equalsIgnoreCase(value));
    }
}
