package com.example.airlinebooking.airline_booking_system.service;

import com.example.airlinebooking.airline_booking_system.dto.flight.FlightRequestDTO;
import com.example.airlinebooking.airline_booking_system.dto.flight.FlightResponseDTO;
import com.example.airlinebooking.airline_booking_system.entity.AirportEntity;
import com.example.airlinebooking.airline_booking_system.exception.ResourceNotFoundException;

import java.time.LocalDateTime;
import java.util.List;

public interface FlightService {

    FlightResponseDTO createFlight(FlightRequestDTO flightRequestDTO) throws IllegalArgumentException;

    FlightResponseDTO getFlightByNumber(String flightNumber) throws IllegalArgumentException, ResourceNotFoundException;

    List<FlightResponseDTO> getFlightsBySourceAndDestination(String sourceAirportCode, String destinationAirportCode)  throws ResourceNotFoundException;

    List<FlightResponseDTO> getFlightsWithinDepartureRange(LocalDateTime start, LocalDateTime end)  throws IllegalArgumentException;

    List<FlightResponseDTO> getFlightsBySource(String sourceAirportCode) throws IllegalArgumentException, ResourceNotFoundException;

    List<FlightResponseDTO> getFlightsByDestination(String destinationAirportCode) throws IllegalArgumentException, ResourceNotFoundException;

    List<FlightResponseDTO> getFlightsByAirlineName(String airlineName) throws IllegalArgumentException;

    List<FlightResponseDTO> getAllFlights();

    boolean deleteFlightByNumber(String flightNumber) throws IllegalArgumentException, ResourceNotFoundException;
}

