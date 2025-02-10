package com.example.airlinebooking.airline_booking_system.service;

import com.example.airlinebooking.airline_booking_system.dto.flight.FlightRequestDTO;
import com.example.airlinebooking.airline_booking_system.dto.flight.FlightResponseDTO;
import com.example.airlinebooking.airline_booking_system.entity.AirportEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface FlightService {

    FlightResponseDTO createFlight(FlightRequestDTO flightRequestDTO);

    FlightResponseDTO getFlightByNumber(FlightRequestDTO flightRequestDTO);

    List<FlightResponseDTO> getFlightsBySourceAndDestination(FlightRequestDTO flightRequestDTO);

    List<FlightResponseDTO> getFlightsWithinDepartureRange(LocalDateTime start, LocalDateTime end);

    List<FlightResponseDTO> getFlightsBySource(FlightRequestDTO flightRequestDTO);

    List<FlightResponseDTO> getFlightsByDestination(FlightRequestDTO flightRequestDTO);

    List<FlightResponseDTO> getFlightsByAirlineName(FlightRequestDTO flightRequestDTO);

    List<FlightResponseDTO> getAllFlights();

    void deleteFlightByNumber(FlightRequestDTO flightRequestDTO);
}

