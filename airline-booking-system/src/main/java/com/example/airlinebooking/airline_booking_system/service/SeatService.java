package com.example.airlinebooking.airline_booking_system.service;

import com.example.airlinebooking.airline_booking_system.dto.seat.SeatRequestDTO;
import com.example.airlinebooking.airline_booking_system.dto.seat.SeatResponseDTO;

import java.util.List;

public interface SeatService {

    SeatResponseDTO createSeat(SeatRequestDTO seatRequestDTO);

    List<SeatResponseDTO> getAllSeatsByFlight(SeatRequestDTO seatRequestDTO);

    List<SeatResponseDTO> getAvailableSeatsByFlight(SeatRequestDTO seatRequestDTO);

    List<SeatResponseDTO> getSeatDetails(SeatRequestDTO seatRequestDTO);

    boolean isSeatAvailable(SeatRequestDTO seatRequestDTO);

    long countAvailableSeats(SeatRequestDTO seatRequestDTO);

    void markSeatsAsUnavailable(SeatRequestDTO seatRequestDTO);
}
