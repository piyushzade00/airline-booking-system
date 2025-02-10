package com.example.airlinebooking.airline_booking_system.dto.seat;

import com.example.airlinebooking.airline_booking_system.enums.SeatType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeatRequestDTO {

    @NotNull(message = "Seat number is required.")
    @Pattern(regexp = "[A-Z]{1}[0-9]{1}", message = "Seat number must follow the pattern: A1, B2, etc.")
    private List<String> seatNumbers;

    @NotNull(message = "Seat type is required.")
    private SeatType seatType;

    @NotNull(message = "Flight number is required.")
    private String flightNumber;
}