package com.example.airlinebooking.airline_booking_system.service;

import com.example.airlinebooking.airline_booking_system.dto.passenger.PassengerRequestDTO;
import com.example.airlinebooking.airline_booking_system.dto.passenger.PassengerResponseDTO;
import com.example.airlinebooking.airline_booking_system.entity.BookingEntity;

import java.awt.print.Pageable;
import java.util.List;

public interface PassengerService {

    PassengerResponseDTO addPassenger(PassengerRequestDTO passengerRequestDTO);

    List<PassengerResponseDTO> getPassengersByBooking(String bookingCode);

    List<PassengerResponseDTO> getPassengersByName(String passengerFullName);

    List<PassengerResponseDTO> getAllPassengers(Pageable pageable);

    boolean deletePassengerById(Long passengerId);
}
