package com.example.airlinebooking.airline_booking_system.controller;

import com.example.airlinebooking.airline_booking_system.dto.booking.BookingRequestDTO;
import com.example.airlinebooking.airline_booking_system.dto.booking.BookingResponseDTO;
import com.example.airlinebooking.airline_booking_system.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "Booking", description = "APIs for managing flight bookings")
@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Operation(summary = "Create Booking", description = "Creates a new flight booking")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'AGENT')")
    @PostMapping("/create-booking")
    public ResponseEntity<BookingResponseDTO> createBooking(@Valid @RequestBody BookingRequestDTO bookingRequestDTO) {
        BookingResponseDTO response = bookingService.createBooking(bookingRequestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Get Booking by Code", description = "Retrieves a booking using its booking code")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'AGENT')")
    @GetMapping("/get-booking-by-code/{bookingCode}")
    public ResponseEntity<BookingResponseDTO> getBookingByCode(@PathVariable String bookingCode) {
        BookingResponseDTO response = bookingService.getBookingByCode(bookingCode);
        return ResponseEntity.ok(response);

    }

    @Operation(summary = "Get Bookings by User", description = "Retrieves all bookings for a specific user")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'AGENT')")
    @GetMapping("/get-booking-by-user/{userName}")
    public ResponseEntity<List<BookingResponseDTO>> getBookingsByUser(@PathVariable String userName) {
        List<BookingResponseDTO> responses = bookingService.getBookingsByUser(userName);
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Get Bookings by Flight", description = "Retrieves all bookings for a specific flight")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'AGENT')")
    @GetMapping("/get-booking-by-flight/{flightNumber}")
    public ResponseEntity<List<BookingResponseDTO>> getBookingsByFlight(@PathVariable String flightNumber) {
        List<BookingResponseDTO> responses = bookingService.getBookingsByFlight(flightNumber);
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Get Bookings Within Timeframe", description = "Retrieves all bookings within a specified timeframe (Admin only)")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get-booking-by-timeframe")
    public ResponseEntity<List<BookingResponseDTO>> getBookingsWithinTimeframe(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        List<BookingResponseDTO> responses = bookingService.getBookingsWithinTimeframe(startTime, endTime);
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Get All Bookings", description = "Retrieves all bookings (Admin only)")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get-all-bookings")
    public ResponseEntity<List<BookingResponseDTO>> getAllBookings(Pageable pageable) {
        List<BookingResponseDTO> responses = bookingService.getAllBookings(pageable);
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Delete Booking by Code", description = "Deletes a booking using its booking code (Admin only)")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete-booking-by-code/{bookingCode}")
    public ResponseEntity<Boolean> deleteBookingByCode(@PathVariable String bookingCode) {
        boolean isDeleted = bookingService.deleteBookingByCode(bookingCode);
        return isDeleted ? ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
