package com.example.airlinebooking.airline_booking_system.dto.booking;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequestDTO {

    @NotNull(message = "User name is required.")
    private String userName;

    @NotNull(message = "Booking code is required.")
    private String bookingCode;

    @NotNull(message = "Travel date is required.")
    @Future(message = "Travel date must be in the future.")
    private LocalDateTime travelDate;

    @NotNull(message = "Number of seats is required.")
    @Positive(message = "Number of seats must be positive.")
    private int numberOfSeats;

    @NotNull(message = "Flight number is required.")
    private String flightNumber;
}
