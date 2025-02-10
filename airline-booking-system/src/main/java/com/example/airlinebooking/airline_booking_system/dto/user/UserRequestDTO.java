package com.example.airlinebooking.airline_booking_system.dto.user;

import com.example.airlinebooking.airline_booking_system.enums.UserRoles;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {

    @NotBlank(message = "Username cannot be blank.")
    private String userName;

    @NotBlank(message = "Password cannot be blank.")
    @Size(min = 8, max = 20, message = "Password must be at least 8 and less than 20 characters.")
    private String password;

    @NotBlank(message = "Email cannot be blank.")
    @Email
    private String email;

    @NotNull(message = "Role must be provided.")
    private UserRoles role;
}
