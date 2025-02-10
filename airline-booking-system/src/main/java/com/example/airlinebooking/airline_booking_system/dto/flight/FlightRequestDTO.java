package com.example.airlinebooking.airline_booking_system.dto.flight;

import com.example.airlinebooking.airline_booking_system.entity.AirportEntity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightRequestDTO {

    @NotNull(message = "Flight number is required.")
    private String flightNumber;

    @NotNull(message = "Airline name is required.")
    private String airlineName;

    @NotNull(message = "Departure time is required.")
    private LocalDateTime departureTime;

    @NotNull(message = "Arrival time is required.")
    private LocalDateTime arrivalTime;

    @NotNull(message = "Total seats are required.")
    @Positive(message = "Total seats must be positive.")
    private int totalSeats;

    @NotNull(message = "Price for each seat is required.")
    @Positive(message = "Price must be positive.")
    private BigDecimal price;

    @NotNull(message = "Source airport code is required.")
    @Size(min = 3, max = 3, message = "Source airport code must be exactly 3 characters.")
    private String sourceAirportCode;

    @NotNull(message = "Destination airport code is required.")
    @Size(min = 3, max = 3, message = "Destination airport code must be exactly 3 characters.")
    private String destinationAirportCode;
}
