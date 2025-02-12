package com.example.airlinebooking.airline_booking_system.controller;

import com.example.airlinebooking.airline_booking_system.dto.user.UserRequestDTO;
import com.example.airlinebooking.airline_booking_system.dto.user.UserResponseDTO;
import com.example.airlinebooking.airline_booking_system.enums.UserRoles;
import com.example.airlinebooking.airline_booking_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.awt.print.Pageable;
import java.util.List;

@Tag(name = "User Management", description = "APIs for managing users in the airline booking system")
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Create a new user", description = "Creates a new user with the specified details.")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'AGENT')")
    @PostMapping("/create-user")
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO userRequestDTO) {
        UserResponseDTO createdUser = userService.createUser(userRequestDTO);
        return ResponseEntity.ok(createdUser);
    }

    @Operation(summary = "Get user by username", description = "Fetches user details by their username.")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'AGENT')")
    @GetMapping("/get-by-username/{userName}")
    public ResponseEntity<UserResponseDTO> getUserByUsername(@PathVariable String userName) {
        UserResponseDTO user = userService.getUserByUsername(userName);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Get user by email", description = "Fetches user details by their email.")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'AGENT')")
    @GetMapping("/get-by-email/{email}")
    public ResponseEntity<UserResponseDTO> getUserByEmail(@PathVariable String email) {
        UserResponseDTO user = userService.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Get all users", description = "Fetches a list of all users. Only accessible by admins.")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get-all-users")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers(Pageable pageable) {
        List<UserResponseDTO> users = userService.getAllUsers(pageable);
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Get users by role", description = "Fetches a list of users filtered by their role. Only accessible by admins.")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get-by-role/{role}")
    public ResponseEntity<List<UserResponseDTO>> getUsersByRole(@PathVariable UserRoles role) {
        List<UserResponseDTO> users = userService.getUsersByRole(role);
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Check if username exists", description = "Checks if a user with the given username exists in the system. Only accessible by admins.")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/exists-by-username/{userName}")
    public ResponseEntity<Boolean> userExistsByUsername(@PathVariable String userName) {
        boolean exists = userService.userExistsByUsername(userName);
        return ResponseEntity.ok(exists);
    }

    @Operation(summary = "Check if email exists", description = "Checks if a user with the given email exists in the system. Only accessible by admins.")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/exists-by-email/{email}")
    public ResponseEntity<Boolean> userExistsByEmail(@PathVariable String email) {
        boolean exists = userService.userExistsByEmail(email);
        return ResponseEntity.ok(exists);
    }

    @Operation(summary = "Delete user by username", description = "Deletes a user based on their username. Only accessible by admins.")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete-by-username/{userName}")
    public ResponseEntity<Boolean> deleteUserByUsername(@PathVariable String userName) {
        boolean deleted = userService.deleteUserByUsername(userName);
        return ResponseEntity.ok(deleted);
    }

    @Operation(summary = "Delete user by email", description = "Deletes a user based on their email. Only accessible by admins.")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete-by-email/{email}")
    public ResponseEntity<Boolean> deleteUserByEmail(@PathVariable String email) {
        boolean deleted = userService.deleteUserByEmail(email);
        return ResponseEntity.ok(deleted);
    }
}
