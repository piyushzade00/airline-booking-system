package com.example.airlinebooking.airline_booking_system.service.impl;

import com.example.airlinebooking.airline_booking_system.dto.seat.SeatRequestDTO;
import com.example.airlinebooking.airline_booking_system.dto.seat.SeatResponseDTO;
import com.example.airlinebooking.airline_booking_system.entity.FlightEntity;
import com.example.airlinebooking.airline_booking_system.entity.SeatEntity;
import com.example.airlinebooking.airline_booking_system.exception.ResourceNotFoundException;
import com.example.airlinebooking.airline_booking_system.mapper.SeatMapper;
import com.example.airlinebooking.airline_booking_system.repository.FlightRepository;
import com.example.airlinebooking.airline_booking_system.repository.SeatRepository;
import com.example.airlinebooking.airline_booking_system.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SeatServiceImpl implements SeatService {

    private final SeatRepository seatRepository;
    private final FlightRepository flightRepository;
    private final SeatMapper seatMapper;

    @Autowired
    public SeatServiceImpl(SeatRepository seatRepository, FlightRepository flightRepository, SeatMapper seatMapper) {
        this.seatRepository = seatRepository;
        this.flightRepository = flightRepository;
        this.seatMapper = seatMapper;
    }

    @Override
    public SeatResponseDTO createSeat(SeatRequestDTO seatRequestDTO) {
        FlightEntity flightEntity = findFlightByFlightNumber(seatRequestDTO.getFlightNumber());

        if(seatRequestDTO.getSeatNumbers().size() > 1)
            throw new IllegalArgumentException("Number of seats is greater than 1");

        String seatNumber = seatRequestDTO.getSeatNumbers().get(0);
        if (seatRepository.existsByFlightAndSeatNumberAndIsAvailableTrue(flightEntity,seatNumber)) {
            throw new IllegalArgumentException("Seat with seat number " + seatNumber + " already exists.");
        }

        SeatEntity seatEntity = seatMapper.toEntity(seatRequestDTO, flightEntity);
        SeatEntity savedSeat = seatRepository.saveSeatEntity(seatEntity);
        return seatMapper.toResponseDTO(savedSeat);
    }

    @Override
    public List<SeatResponseDTO> getAllSeatsByFlight(SeatRequestDTO seatRequestDTO) {
        FlightEntity flightEntity = findFlightByFlightNumber(seatRequestDTO.getFlightNumber());

        return seatRepository.findAllSeatsByFlight(flightEntity)
                .stream()
                .map(seatMapper :: toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<SeatResponseDTO> getAvailableSeatsByFlight(SeatRequestDTO seatRequestDTO) {
        FlightEntity flightEntity = findFlightByFlightNumber(seatRequestDTO.getFlightNumber());

        return seatRepository.findByFlightAndIsAvailableTrue(flightEntity)
                .stream()
                .map(seatMapper :: toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<SeatResponseDTO> getSeatDetails(SeatRequestDTO seatRequestDTO) {
        FlightEntity flightEntity = findFlightByFlightNumber(seatRequestDTO.getFlightNumber());

        List<SeatResponseDTO> seatResponseDTO = new ArrayList<>();

        for(String seatNumber : seatRequestDTO.getSeatNumbers()) {
            SeatEntity seatEntity = seatRepository.findByFlightAndSeatNumber(flightEntity, seatNumber)
                    .orElseThrow(() -> new ResourceNotFoundException("Seat not found for seat number: " + seatNumber));

            seatResponseDTO.add(seatMapper.toResponseDTO(seatEntity));
        }

        return seatResponseDTO;
    }

    @Override
    public boolean isSeatAvailable(SeatRequestDTO seatRequestDTO) {
        int seatSize = seatRequestDTO.getSeatNumbers().size();
        if(seatSize < 1)
            throw new IllegalArgumentException("Please provide at least one seat number.");
        if(seatSize > 1)
            throw new ResourceNotFoundException("Please provide one seat number.");

        FlightEntity flightEntity = findFlightByFlightNumber(seatRequestDTO.getFlightNumber());

        return seatRepository.existsByFlightAndSeatNumberAndIsAvailableTrue(flightEntity, seatRequestDTO.getSeatNumbers().get(0));
    }

    @Override
    public long countAvailableSeats(SeatRequestDTO seatRequestDTO) {
        FlightEntity flightEntity = findFlightByFlightNumber(seatRequestDTO.getFlightNumber());
        return seatRepository.countByFlightAndIsAvailableTrue(flightEntity);
    }

    @Transactional
    @Override
    public void markSeatsAsUnavailable(SeatRequestDTO seatRequestDTO) {
        FlightEntity flightEntity = findFlightByFlightNumber(seatRequestDTO.getFlightNumber());
        seatRepository.markSeatsAsUnavailable(flightEntity, seatRequestDTO.getSeatNumbers());
    }

    private FlightEntity findFlightByFlightNumber(String flightNumber) {
        if(flightNumber == null || flightNumber.trim().isEmpty()) {
            throw new ResourceNotFoundException("Flight number is empty");
        }

        return flightRepository.findFlightEntityByFlightNumber(flightNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found with number: " + flightNumber));
    }
}
