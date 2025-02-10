package com.example.airlinebooking.airline_booking_system.dto.seat;

import com.example.airlinebooking.airline_booking_system.enums.SeatType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeatResponseDTO {

    private Long seatId;
    private String seatNumber;
    private SeatType seatType;
    private boolean isAvailable;
    private String flightNumber;
}