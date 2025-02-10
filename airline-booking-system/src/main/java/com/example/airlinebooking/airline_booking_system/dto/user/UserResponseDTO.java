package com.example.airlinebooking.airline_booking_system.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {

    private Long userId;
    private String userName;
    private String displayName;
    private String email;
    private LocalDateTime createdAt;
}
