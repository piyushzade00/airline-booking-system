package com.example.airlinebooking.airline_booking_system.service;

import com.example.airlinebooking.airline_booking_system.dto.booking.BookingRequestDTO;
import com.example.airlinebooking.airline_booking_system.dto.booking.BookingResponseDTO;
import com.example.airlinebooking.airline_booking_system.exception.ResourceNotFoundException;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingService {

    BookingResponseDTO createBooking(BookingRequestDTO bookingRequestDTO) throws IllegalArgumentException, ResourceNotFoundException;

    BookingResponseDTO getBookingByCode(String bookingCode) throws IllegalArgumentException, ResourceNotFoundException;

    List<BookingResponseDTO> getBookingsByUser(String userName) throws IllegalArgumentException, ResourceNotFoundException;

    List<BookingResponseDTO> getBookingsByFlight(String flightNumber) throws IllegalArgumentException, ResourceNotFoundException;

    List<BookingResponseDTO> getBookingsWithinTimeframe(LocalDateTime start, LocalDateTime end) throws IllegalArgumentException;

    List<BookingResponseDTO> getAllBookings();

    boolean deleteBookingByCode(String bookingCode) throws IllegalArgumentException, ResourceNotFoundException;
}
