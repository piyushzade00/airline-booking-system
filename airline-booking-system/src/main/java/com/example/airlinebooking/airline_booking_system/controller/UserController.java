package com.example.airlinebooking.airline_booking_system.controller;

import com.example.airlinebooking.airline_booking_system.dto.user.UserRequestDTO;
import com.example.airlinebooking.airline_booking_system.dto.user.UserResponseDTO;
import com.example.airlinebooking.airline_booking_system.enums.UserRoles;
import com.example.airlinebooking.airline_booking_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'AGENT')")
    @PostMapping("/create-user")
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO userRequestDTO) {
        UserResponseDTO createdUser = userService.createUser(userRequestDTO);
        return ResponseEntity.ok(createdUser);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'AGENT')")
    @GetMapping("/get-by-username/{userName}")
    public ResponseEntity<UserResponseDTO> getUserByUsername(@PathVariable String userName) {
        UserResponseDTO user = userService.getUserByUsername(userName);
        return ResponseEntity.ok(user);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'AGENT')")
    @GetMapping("/get-by-email/{email}")
    public ResponseEntity<UserResponseDTO> getUserByEmail(@PathVariable String email) {
        UserResponseDTO user = userService.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get-all-users")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get-by-role/{role}")
    public ResponseEntity<List<UserResponseDTO>> getUsersByRole(@PathVariable UserRoles role) {
        List<UserResponseDTO> users = userService.getUsersByRole(role);
        return ResponseEntity.ok(users);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/exists-by-username/{userName}")
    public ResponseEntity<Boolean> userExistsByUsername(@PathVariable String userName) {
        boolean exists = userService.userExistsByUsername(userName);
        return ResponseEntity.ok(exists);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/exists-by-email/{email}")
    public ResponseEntity<Boolean> userExistsByEmail(@PathVariable String email) {
        boolean exists = userService.userExistsByEmail(email);
        return ResponseEntity.ok(exists);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete-by-username/{userName}")
    public ResponseEntity<Boolean> deleteUserByUsername(@PathVariable String userName) {
        boolean deleted = userService.deleteUserByUsername(userName);
        return ResponseEntity.ok(deleted);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete-by-email/{email}")
    public ResponseEntity<Boolean> deleteUserByEmail(@PathVariable String email) {
        boolean deleted = userService.deleteUserByEmail(email);
        return ResponseEntity.ok(deleted);
    }
}
