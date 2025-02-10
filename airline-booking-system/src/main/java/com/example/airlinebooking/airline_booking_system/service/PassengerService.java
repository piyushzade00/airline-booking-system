package com.example.airlinebooking.airline_booking_system.service;

import com.example.airlinebooking.airline_booking_system.dto.passenger.PassengerRequestDTO;
import com.example.airlinebooking.airline_booking_system.dto.passenger.PassengerResponseDTO;
import com.example.airlinebooking.airline_booking_system.entity.BookingEntity;

import java.util.List;

public interface PassengerService {

    PassengerResponseDTO addPassenger(PassengerRequestDTO passengerRequestDTO);

    List<PassengerResponseDTO> getPassengersByBooking(PassengerRequestDTO passengerRequestDTO);

    List<PassengerResponseDTO> getPassengersByName(PassengerRequestDTO passengerRequestDTO);

    List<PassengerResponseDTO> getAllPassengers();

    void deletePassengerById(Long passengerId);
}
