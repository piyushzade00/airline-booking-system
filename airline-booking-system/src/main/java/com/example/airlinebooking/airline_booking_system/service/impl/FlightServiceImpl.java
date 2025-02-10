package com.example.airlinebooking.airline_booking_system.service.impl;

import com.example.airlinebooking.airline_booking_system.dto.flight.FlightRequestDTO;
import com.example.airlinebooking.airline_booking_system.dto.flight.FlightResponseDTO;
import com.example.airlinebooking.airline_booking_system.entity.FlightEntity;
import com.example.airlinebooking.airline_booking_system.entity.AirportEntity;
import com.example.airlinebooking.airline_booking_system.exception.ResourceNotFoundException;
import com.example.airlinebooking.airline_booking_system.mapper.FlightMapper;
import com.example.airlinebooking.airline_booking_system.repository.AirportRepository;
import com.example.airlinebooking.airline_booking_system.repository.FlightRepository;
import com.example.airlinebooking.airline_booking_system.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;
    private final AirportRepository airportRepository;
    private final FlightMapper flightMapper;

    @Autowired
    public FlightServiceImpl(FlightRepository flightRepository, AirportRepository airportRepository, FlightMapper flightMapper) {
        this.flightRepository = flightRepository;
        this.airportRepository = airportRepository;
        this.flightMapper = flightMapper;
    }

    @Override
    public FlightResponseDTO createFlight(FlightRequestDTO flightRequestDTO) {
        if(flightRequestDTO.getDepartureTime().isAfter(flightRequestDTO.getArrivalTime())) {
            throw new IllegalArgumentException("Departure time cannot be after arrival time.");
        }

        Map<String, AirportEntity> sourceAndDestinationEntityMap = getSourceAndDestinationEntity(flightRequestDTO);

        FlightEntity flightEntity = flightMapper.toEntity(flightRequestDTO, sourceAndDestinationEntityMap.get("source"), sourceAndDestinationEntityMap.get("destination"));
        FlightEntity savedFlight = flightRepository.saveFlightEntity(flightEntity);
        return flightMapper.toResponseDTO(savedFlight);
    }

    @Override
    public FlightResponseDTO getFlightByNumber(FlightRequestDTO flightRequestDTO) {
        String flightNumber = flightRequestDTO.getFlightNumber();
        if(flightNumber == null ||flightNumber.trim().isEmpty()) {
            throw new ResourceNotFoundException("Invalid flight number");
        }

        FlightEntity flightEntity = flightRepository.findFlightEntityByFlightNumber(flightNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found with number: " + flightNumber));
        return flightMapper.toResponseDTO(flightEntity);
    }

    @Override
    public List<FlightResponseDTO> getFlightsBySourceAndDestination(FlightRequestDTO flightRequestDTO) {
        Map<String, AirportEntity> sourceAndDestinationEntityMap = getSourceAndDestinationEntity(flightRequestDTO);

        return flightRepository.findBySourceAirportEntityAndDestinationAirportEntity(sourceAndDestinationEntityMap.get("source"), sourceAndDestinationEntityMap.get("destination"))
                .stream()
                .map(flightMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<FlightResponseDTO> getFlightsWithinDepartureRange(LocalDateTime start, LocalDateTime end) {
        if(start.isAfter(end)) {
            throw new IllegalArgumentException("Start time cannot be after end time.");
        }
        return flightRepository.findByDepartureTimeBetween(start, end)
                .stream()
                .map(flightMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<FlightResponseDTO> getFlightsBySource(FlightRequestDTO flightRequestDTO) {
        AirportEntity sourceAirportEntity = airportRepository.findByAirportCode(flightRequestDTO.getSourceAirportCode())
                .orElseThrow(()-> new ResourceNotFoundException("Airport not found with code: " + flightRequestDTO.getSourceAirportCode()));

        return flightRepository.findBySourceAirportEntity(sourceAirportEntity)
                .stream()
                .map(flightMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<FlightResponseDTO> getFlightsByDestination(FlightRequestDTO flightRequestDTO) {
        AirportEntity destinationAirportEntity = airportRepository.findByAirportCode(flightRequestDTO.getDestinationAirportCode())
                .orElseThrow(()-> new ResourceNotFoundException("Airport not found with code: " + flightRequestDTO.getDestinationAirportCode()));

        return flightRepository.findByDestinationAirportEntity(destinationAirportEntity)
                .stream()
                .map(flightMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<FlightResponseDTO> getFlightsByAirlineName(FlightRequestDTO flightRequestDTO) {
        String airlineName = flightRequestDTO.getAirlineName();
        if(airlineName == null || airlineName.trim().isEmpty()) {
            throw new ResourceNotFoundException("Airline name cannot be null or empty.");
        }
        return flightRepository.findByAirlineName(airlineName)
                .stream()
                .map(flightMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<FlightResponseDTO> getAllFlights() {
        return flightRepository.findAllFlights()
                .stream()
                .map(flightMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteFlightByNumber(FlightRequestDTO flightRequestDTO) {
        String flightNumber = flightRequestDTO.getFlightNumber();
        if (flightNumber == null || flightNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Flight number cannot be null or empty.");
        }
       flightRepository.findFlightEntityByFlightNumber(flightNumber)
               .orElseThrow(() -> new ResourceNotFoundException("Flight not found with number: " + flightNumber));
        flightRepository.deleteByFlightNumber(flightNumber);
    }

    public Map<String, AirportEntity> getSourceAndDestinationEntity(FlightRequestDTO flightRequestDTO) {
        String sourceAirportCode = flightRequestDTO.getSourceAirportCode();
        if (sourceAirportCode == null || sourceAirportCode.trim().isEmpty()) {
            throw new ResourceNotFoundException("Source airport code is empty");
        }

        String destinationAirportCode = flightRequestDTO.getDestinationAirportCode();
        if (destinationAirportCode == null || destinationAirportCode.trim().isEmpty()) {
            throw new ResourceNotFoundException("Destination airport code is empty");
        }

        AirportEntity sourceAirportEntity = airportRepository.findByAirportCode(sourceAirportCode)
                .orElseThrow(() -> new ResourceNotFoundException("Source airport not found"));

        AirportEntity destinationAirportEntity = airportRepository.findByAirportCode(destinationAirportCode)
                .orElseThrow(() -> new ResourceNotFoundException("Destination airport not found"));

        Map<String, AirportEntity> result = new HashMap<>();
        result.put("source", sourceAirportEntity);
        result.put("destination", destinationAirportEntity);

        return result;
    }
}
