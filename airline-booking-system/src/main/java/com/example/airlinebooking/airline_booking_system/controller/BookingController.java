package com.example.airlinebooking.airline_booking_system.controller;

import com.example.airlinebooking.airline_booking_system.dto.booking.BookingRequestDTO;
import com.example.airlinebooking.airline_booking_system.dto.booking.BookingResponseDTO;
import com.example.airlinebooking.airline_booking_system.exception.ResourceNotFoundException;
import com.example.airlinebooking.airline_booking_system.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'AGENT')")
    @PostMapping("/create-booking")
    public ResponseEntity<BookingResponseDTO> createBooking(@Valid @RequestBody BookingRequestDTO bookingRequestDTO) {
        BookingResponseDTO response = bookingService.createBooking(bookingRequestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'AGENT')")
    @GetMapping("/get-booking-by-code/{bookingCode}")
    public ResponseEntity<BookingResponseDTO> getBookingByCode(@PathVariable String bookingCode) {
        BookingResponseDTO response = bookingService.getBookingByCode(bookingCode);
        return ResponseEntity.ok(response);

    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'AGENT')")
    @GetMapping("/get-booking-by-user/{userName}")
    public ResponseEntity<List<BookingResponseDTO>> getBookingsByUser(@PathVariable String userName) {
        List<BookingResponseDTO> responses = bookingService.getBookingsByUser(userName);
        return ResponseEntity.ok(responses);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'AGENT')")
    @GetMapping("/get-booking-by-flight/{flightNumber}")
    public ResponseEntity<List<BookingResponseDTO>> getBookingsByFlight(@PathVariable String flightNumber) {
        List<BookingResponseDTO> responses = bookingService.getBookingsByFlight(flightNumber);
        return ResponseEntity.ok(responses);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get-booking-by-timeframe")
    public ResponseEntity<List<BookingResponseDTO>> getBookingsWithinTimeframe(
            @RequestParam("start") String start,
            @RequestParam("end") String end) {
        LocalDateTime startTime = LocalDateTime.parse(start);
        LocalDateTime endTime = LocalDateTime.parse(end);
        List<BookingResponseDTO> responses = bookingService.getBookingsWithinTimeframe(startTime, endTime);
        return ResponseEntity.ok(responses);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get-all-bookings")
    public ResponseEntity<List<BookingResponseDTO>> getAllBookings() {
        List<BookingResponseDTO> responses = bookingService.getAllBookings();
        return ResponseEntity.ok(responses);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/delete-booking-by-code/{bookingCode}")
    public ResponseEntity<Boolean> deleteBookingByCode(@PathVariable String bookingCode) {
        boolean isDeleted = bookingService.deleteBookingByCode(bookingCode);
        return ResponseEntity.ok(isDeleted);
    }
}
