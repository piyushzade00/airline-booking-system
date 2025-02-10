package com.example.airlinebooking.airline_booking_system.service.impl;

import com.example.airlinebooking.airline_booking_system.dto.airport.AirportRequestDTO;
import com.example.airlinebooking.airline_booking_system.dto.airport.AirportResponseDTO;
import com.example.airlinebooking.airline_booking_system.entity.AirportEntity;
import com.example.airlinebooking.airline_booking_system.exception.ResourceNotFoundException;
import com.example.airlinebooking.airline_booking_system.mapper.AirportMapper;
import com.example.airlinebooking.airline_booking_system.repository.AirportRepository;
import com.example.airlinebooking.airline_booking_system.service.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AirportServiceImpl implements AirportService {

    private final AirportRepository airportRepository;
    private final AirportMapper airportMapper;

    @Autowired
    public AirportServiceImpl(AirportRepository airportRepository, AirportMapper airportMapper) {
        this.airportRepository = airportRepository;
        this.airportMapper = airportMapper;
    }

    @Override
    public AirportResponseDTO addAirport(AirportRequestDTO airportRequestDTO) {
        if (airportRepository.existsByAirportCode(airportRequestDTO.getAirportCode())) {
            throw new IllegalArgumentException("Airport with code " + airportRequestDTO.getAirportCode() + " already exists.");
        }

        if (airportRepository.findByAirportName(airportRequestDTO.getAirportName()).isPresent()) {
            throw new IllegalArgumentException("Airport with name " + airportRequestDTO.getAirportName() + " already exists.");
        }

        AirportEntity airportEntity = airportMapper.toEntity(airportRequestDTO);
        AirportEntity savedAirport = airportRepository.saveAirportEntity(airportEntity);
        return airportMapper.toResponseDTO(savedAirport);
    }

    @Override
    public List<AirportResponseDTO> getAllAirports() {
        return airportRepository.findAllAirports()
                .stream()
                .map(airportMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AirportResponseDTO getAirportByCode(String airportCode) {
        if (airportCode == null || airportCode.trim().isEmpty()) {
            throw new IllegalArgumentException("Airport code cannot be null or empty");
        }
        AirportEntity airportEntity = airportRepository.findByAirportCode(airportCode)
                .orElseThrow(() -> new ResourceNotFoundException("Airport not found with code: " + airportCode));
        return airportMapper.toResponseDTO(airportEntity);
    }

    @Override
    public AirportResponseDTO getAirportByName(String airportName) {
        if (airportName == null || airportName.trim().isEmpty()) {
            throw new IllegalArgumentException("Airport name cannot be null or empty");
        }
        AirportEntity airportEntity = airportRepository.findByAirportName(airportName)
                .orElseThrow(() -> new ResourceNotFoundException("Airport not found with code: " + airportName));
        return airportMapper.toResponseDTO(airportEntity);
    }

    @Override
    public List<AirportResponseDTO> getAirportsByLocation(String location) {
        if (location == null || location.trim().isEmpty()) {
            throw new IllegalArgumentException("Location cannot be null or empty");
        }
        List<AirportEntity> airportEntities = airportRepository.findByLocation(location);
        return airportEntities.stream()
                .map(airportMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public boolean doesAirportExist(String airportCode) {
        if(airportCode == null || airportCode.trim().isEmpty()) {
            throw new IllegalArgumentException("Airport code cannot be null or empty");
        }
        return airportRepository.existsByAirportCode(airportCode);
    }
    
    @Override
    public boolean deleteAirportByCode(String airportCode) {
        if (airportCode == null || airportCode.trim().isEmpty()) {
            throw new IllegalArgumentException("Airport code cannot be null or empty");
        }
        if (!airportRepository.existsByAirportCode(airportCode)) {
            throw new ResourceNotFoundException("Airport with code " + airportCode + " not found.");
        }
        return airportRepository.deleteByAirportCode(airportCode);
    }

    @Override
    public boolean deleteAirportByName(String airportName) {
        if (airportName == null || airportName.trim().isEmpty()) {
            throw new IllegalArgumentException("Airport name cannot be null or empty");
        }
        airportRepository.findByAirportName(airportName)
                .orElseThrow(() -> new ResourceNotFoundException("Airport not found with code: " + airportName));

        return airportRepository.deleteByAirportName(airportName);
    }
}