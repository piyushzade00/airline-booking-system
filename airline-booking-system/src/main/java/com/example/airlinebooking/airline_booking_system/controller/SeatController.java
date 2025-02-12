package com.example.airlinebooking.airline_booking_system.controller;

import com.example.airlinebooking.airline_booking_system.dto.seat.SeatRequestDTO;
import com.example.airlinebooking.airline_booking_system.dto.seat.SeatResponseDTO;
import com.example.airlinebooking.airline_booking_system.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "Seat Management", description = "APIs for managing seats in flights")
@RestController
@RequestMapping("/api/seats")
public class SeatController {

    private final SeatService seatService;

    @Autowired
    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    @Operation(summary = "Create a new seat", description = "Allows admin to create a new seat for a flight")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create-seat")
    public ResponseEntity<SeatResponseDTO> createSeat(@RequestBody SeatRequestDTO seatRequestDTO) {
        SeatResponseDTO seatResponse = seatService.createSeat(seatRequestDTO);
        return ResponseEntity.ok(seatResponse);
    }

    @Operation(summary = "Get all seats for a flight", description = "Retrieves all seats available for a given flight")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'AGENT')")
    @GetMapping("/get-all-seats-by-flight/{flightNumber}")
    public ResponseEntity<List<SeatResponseDTO>> getAllSeatsByFlight(@PathVariable String flightNumber) {
        List<SeatResponseDTO> seats = seatService.getAllSeatsByFlight(flightNumber);
        return ResponseEntity.ok(seats);
    }

    @Operation(summary = "Get available seats", description = "Fetches all available (unbooked) seats for a specific flight")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'AGENT')")
    @GetMapping("/get-available-seats-by-flight/{flightNumber}")
    public ResponseEntity<List<SeatResponseDTO>> getAvailableSeatsByFlight(@PathVariable String flightNumber) {
        List<SeatResponseDTO> availableSeats = seatService.getAvailableSeatsByFlight(flightNumber);
        return ResponseEntity.ok(availableSeats);
    }

    @Operation(summary = "Get seat details", description = "Fetches details of specific seats for a flight")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'AGENT')")
    @PostMapping("/get-seat-details")
    public ResponseEntity<List<SeatResponseDTO>> getSeatDetails(
            @RequestParam String flightNumber,
            @RequestBody List<String> seatNumbers
    ) {
        List<SeatResponseDTO> seatDetails = seatService.getSeatDetails(flightNumber, seatNumbers);
        return ResponseEntity.ok(seatDetails);
    }

    @Operation(summary = "Check seat availability", description = "Checks if specific seats are available for booking on a flight")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'AGENT')")
    @PostMapping("/is-seat-available")
    public ResponseEntity<Boolean> isSeatAvailable(
            @RequestParam String flightNumber,
            @RequestBody List<String> seatNumbers
    ) {
        boolean isAvailable = seatService.isSeatAvailable(flightNumber, seatNumbers);
        return ResponseEntity.ok(isAvailable);
    }

    @Operation(summary = "Count available seats", description = "Returns the number of available seats for a specific flight")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'AGENT')")
    @GetMapping("/count-available-seats")
    public ResponseEntity<Long> countAvailableSeats(@RequestParam String flightNumber) {
        long count = seatService.countAvailableSeats(flightNumber);
        return ResponseEntity.ok(count);
    }

    @Operation(summary = "Mark seats as unavailable", description = "Allows admin to mark specific seats as unavailable")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/mark-seats-unavailable")
    public ResponseEntity<Void> markSeatsAsUnavailable(
            @RequestParam String flightNumber,
            @RequestBody List<String> seatNumbers
    ) {
        seatService.markSeatsAsUnavailable(flightNumber, seatNumbers);
        return ResponseEntity.noContent().build();
    }
}

