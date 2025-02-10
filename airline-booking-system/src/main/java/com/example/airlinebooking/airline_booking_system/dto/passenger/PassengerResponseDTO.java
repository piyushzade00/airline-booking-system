package com.example.airlinebooking.airline_booking_system.dto.passenger;

import com.example.airlinebooking.airline_booking_system.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PassengerResponseDTO {

    private Long passengerId;
    private String passengerFullName;
    private Gender gender;
    private int age;
    private String bookingCode;
    private String seatNumber; // Return seat details if assigned
}