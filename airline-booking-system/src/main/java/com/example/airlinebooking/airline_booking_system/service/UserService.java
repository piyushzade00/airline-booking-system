package com.example.airlinebooking.airline_booking_system.service;

import com.example.airlinebooking.airline_booking_system.dto.user.UserRequestDTO;
import com.example.airlinebooking.airline_booking_system.dto.user.UserResponseDTO;
import com.example.airlinebooking.airline_booking_system.enums.UserRoles;

import java.awt.print.Pageable;
import java.util.List;

public interface UserService {

    UserResponseDTO createUser(UserRequestDTO userRequestDTO);

    UserResponseDTO getUserByUsername(String userName);

    UserResponseDTO getUserByEmail(String email);

    List<UserResponseDTO> getAllUsers(Pageable pageable);

    List<UserResponseDTO> getUsersByRole(UserRoles role);

    boolean userExistsByUsername(String userName);

    boolean userExistsByEmail(String email);

    boolean deleteUserByUsername(String userName);

    boolean deleteUserByEmail(String email);
}
