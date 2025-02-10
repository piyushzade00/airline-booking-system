package com.example.airlinebooking.airline_booking_system.service;

import com.example.airlinebooking.airline_booking_system.dto.airport.AirportRequestDTO;
import com.example.airlinebooking.airline_booking_system.dto.airport.AirportResponseDTO;
import com.example.airlinebooking.airline_booking_system.exception.ResourceNotFoundException;

import java.util.List;

public interface AirportService {

    // Add a new airport
    AirportResponseDTO addAirport(AirportRequestDTO airportRequestDTO) throws IllegalArgumentException;

    // Retrieve a list of all airports
    List<AirportResponseDTO> getAllAirports();

    // Retrieve a specific airport by code
    AirportResponseDTO getAirportByCode(String airportCode) throws IllegalArgumentException, ResourceNotFoundException;

    // Retrieve a specific airport by name
    AirportResponseDTO getAirportByName(String airportName) throws IllegalArgumentException, ResourceNotFoundException;

    // Retrieve airports by location
    List<AirportResponseDTO> getAirportsByLocation(String location) throws IllegalArgumentException;

    //Check if airport exists by airport code
    boolean doesAirportExist(String airportCode);

    // Delete an airport by code
    boolean deleteAirportByCode(String airportCode) throws IllegalArgumentException, ResourceNotFoundException;

    // Delete an airport by name
    boolean deleteAirportByName(String airportName) throws IllegalArgumentException, ResourceNotFoundException;
}
