package com.example.airlinebooking.airline_booking_system.dto.passenger;

import com.example.airlinebooking.airline_booking_system.enums.Gender;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PassengerRequestDTO {

    @NotNull(message = "Passenger full name is required.")
    private String passengerFullName;

    @NotNull(message = "Gender is required.")
    private Gender gender;

    @NotNull(message = "Age is required.")
    @Min(value = 1, message = "Age must be at least 1.")
    private int age;

    @NotNull(message = "Booking code is required.")
    private String bookingCode;

    private String seatNumber;
}