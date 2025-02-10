package com.example.airlinebooking.airline_booking_system.service;

import com.example.airlinebooking.airline_booking_system.dto.airport.AirportRequestDTO;
import com.example.airlinebooking.airline_booking_system.dto.airport.AirportResponseDTO;

import java.util.List;

public interface AirportService {

    // Add a new airport
    AirportResponseDTO addAirport(AirportRequestDTO airportRequestDTO);

    // Retrieve a list of all airports
    List<AirportResponseDTO> getAllAirports();

    // Retrieve a specific airport by code
    AirportResponseDTO getAirportByCode(AirportRequestDTO airportRequestDTO);

    // Retrieve a specific airport by name
    AirportResponseDTO getAirportByName(AirportRequestDTO airportRequestDTO);

    // Retrieve airports by location
    List<AirportResponseDTO> getAirportsByLocation(AirportRequestDTO airportRequestDTO);

    //Check if airport exists by airport code
    boolean isAirportExist(AirportRequestDTO airportRequestDTO);

    // Delete an airport by code
    void deleteAirportByCode(AirportRequestDTO airportRequestDTO);

    // Delete an airport by name
    void deleteAirportByName(AirportRequestDTO airportRequestDTO);
}
