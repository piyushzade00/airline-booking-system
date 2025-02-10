package com.example.airlinebooking.airline_booking_system.service;

import com.example.airlinebooking.airline_booking_system.dto.user.UserRequestDTO;
import com.example.airlinebooking.airline_booking_system.dto.user.UserResponseDTO;
import com.example.airlinebooking.airline_booking_system.enums.UserRoles;

import java.util.List;

public interface UserService {

    UserResponseDTO createUser(UserRequestDTO userRequestDTO);

    UserResponseDTO getUserByUsername(UserRequestDTO userRequestDTO);

    UserResponseDTO getUserByEmail(UserRequestDTO userRequestDTO);

    List<UserResponseDTO> getAllUsers();

    List<UserResponseDTO> getUsersByRole(UserRequestDTO userRequestDTO);

    boolean userExistsByUsername(UserRequestDTO userRequestDTO);

    boolean userExistsByEmail(UserRequestDTO userRequestDTO);

    void deleteUserByUsername(UserRequestDTO userRequestDTO);

    void deleteUserByEmail(UserRequestDTO userRequestDTO);
}
