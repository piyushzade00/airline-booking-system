package com.example.airlinebooking.airline_booking_system.dto.airport;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AirportRequestDTO {

    @NotNull(message = "Airport name is required")
    @Size(min = 3, max = 50, message = "Airport name must be between 3 and 50 characters")
    private String airportName;

    @NotNull(message = "Airport code is required")
    @Pattern(regexp = "^[A-Z]{3}$", message = "Airport code must be exactly 3 uppercase letters")
    private String airportCode;

    @NotNull(message = "Location is required")
    @Size(min = 2, max = 100, message = "Location must be between 2 and 100 characters")
    private String location;
}
