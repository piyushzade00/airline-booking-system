package com.example.airlinebooking.airline_booking_system.service.impl;

import com.example.airlinebooking.airline_booking_system.dto.passenger.PassengerRequestDTO;
import com.example.airlinebooking.airline_booking_system.dto.passenger.PassengerResponseDTO;
import com.example.airlinebooking.airline_booking_system.entity.BookingEntity;
import com.example.airlinebooking.airline_booking_system.entity.PassengerEntity;
import com.example.airlinebooking.airline_booking_system.entity.SeatEntity;
import com.example.airlinebooking.airline_booking_system.exception.ResourceNotFoundException;
import com.example.airlinebooking.airline_booking_system.mapper.PassengerMapper;
import com.example.airlinebooking.airline_booking_system.repository.BookingRepository;
import com.example.airlinebooking.airline_booking_system.repository.PassengerRepository;
import com.example.airlinebooking.airline_booking_system.repository.SeatRepository;
import com.example.airlinebooking.airline_booking_system.service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PassengerServiceImpl implements PassengerService {

    private final PassengerRepository passengerRepository;
    private final BookingRepository bookingRepository;
    private final SeatRepository seatRepository;
    private final PassengerMapper passengerMapper;

    @Autowired
    public PassengerServiceImpl(PassengerRepository passengerRepository,
                                BookingRepository bookingRepository,
                                SeatRepository seatRepository,
                                PassengerMapper passengerMapper) {

        this.passengerRepository = passengerRepository;
        this.bookingRepository = bookingRepository;
        this.seatRepository = seatRepository;
        this.passengerMapper = passengerMapper;
    }

    @Override
    public PassengerResponseDTO addPassenger(PassengerRequestDTO passengerRequestDTO) {
        String bookingCode = passengerRequestDTO.getBookingCode();
        if(bookingCode == null || bookingCode.trim().isEmpty())
        {
            throw new ResourceNotFoundException("Booking code is empty");
        }
        BookingEntity bookingEntity = bookingRepository.findByBookingCode(bookingCode)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        String seatNumber = passengerRequestDTO.getSeatNumber();
        if(seatNumber == null || seatNumber.trim().isEmpty())
        {
            throw new ResourceNotFoundException("Seat number is empty");
        }

        SeatEntity seatEntity = seatRepository.findByFlightAndSeatNumber(bookingEntity.getFlight(), seatNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Seat number not found"));

        PassengerEntity passengerEntity = passengerMapper.toEntity(passengerRequestDTO,bookingEntity,seatEntity);
        PassengerEntity savedPassenger = passengerRepository.savePassengerEntity(passengerEntity);
        return passengerMapper.toResponseDTO(savedPassenger);
    }

    @Override
    public List<PassengerResponseDTO> getPassengersByBooking(String bookingCode) {
        if(bookingCode == null || bookingCode.trim().isEmpty())
        {
            throw new ResourceNotFoundException("Booking code is empty");
        }

        BookingEntity booking = bookingRepository.findByBookingCode(bookingCode)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        return passengerRepository.findPassengerByBooking(booking)
                .stream()
                .map(passengerMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PassengerResponseDTO> getPassengersByName(String passengerFullName) {
        if(passengerFullName == null || passengerFullName.trim().isEmpty())
        {
            throw new ResourceNotFoundException("Passenger full name is empty");
        }
        return passengerRepository.findByPassengerFullName(passengerFullName)
                .stream()
                .map(passengerMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PassengerResponseDTO> getAllPassengers() {
        return passengerRepository.findAllPassengers()
                .stream()
                .map(passengerMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public boolean deletePassengerById(Long passengerId) {
        if (!passengerRepository.existsById(passengerId)) {
            throw new ResourceNotFoundException("Passenger not found with ID: " + passengerId);
        }
        return passengerRepository.deleteByPassengerId(passengerId);
    }
}
