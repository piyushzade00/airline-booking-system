package com.example.airlinebooking.airline_booking_system.service;

import com.example.airlinebooking.airline_booking_system.dto.seat.SeatRequestDTO;
import com.example.airlinebooking.airline_booking_system.dto.seat.SeatResponseDTO;

import java.util.List;

public interface SeatService {

    SeatResponseDTO createSeat(SeatRequestDTO seatRequestDTO);

    List<SeatResponseDTO> getAllSeatsByFlight(String flightNumber);

    List<SeatResponseDTO> getAvailableSeatsByFlight(String flightNumber);

    List<SeatResponseDTO> getSeatDetails(String flightNumber, List<String> seatNumbers);

    boolean isSeatAvailable(String flightNumber, List<String> seatNumbers);

    long countAvailableSeats(String flightNumber);

    void markSeatsAsUnavailable(String flightNumber, List<String> seatNumbers);
}
