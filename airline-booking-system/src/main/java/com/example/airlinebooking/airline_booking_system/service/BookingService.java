package com.example.airlinebooking.airline_booking_system.service;

import com.example.airlinebooking.airline_booking_system.dto.booking.BookingRequestDTO;
import com.example.airlinebooking.airline_booking_system.dto.booking.BookingResponseDTO;
import com.example.airlinebooking.airline_booking_system.entity.FlightEntity;
import com.example.airlinebooking.airline_booking_system.entity.UserEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingService {

    BookingResponseDTO createBooking(BookingRequestDTO bookingRequestDTO);

    BookingResponseDTO getBookingByCode(BookingRequestDTO bookingRequestDTO);

    List<BookingResponseDTO> getBookingsByUser(BookingRequestDTO bookingRequestDTO);

    List<BookingResponseDTO> getBookingsByFlight(BookingRequestDTO bookingRequestDTO);

    List<BookingResponseDTO> getBookingsWithinTimeframe(LocalDateTime start, LocalDateTime end);

    List<BookingResponseDTO> getAllBookings();

    void deleteBookingByCode(BookingRequestDTO bookingRequestDTO);
}
