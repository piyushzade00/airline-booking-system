package com.example.airlinebooking.airline_booking_system.controller;

import com.example.airlinebooking.airline_booking_system.dto.seat.SeatRequestDTO;
import com.example.airlinebooking.airline_booking_system.dto.seat.SeatResponseDTO;
import com.example.airlinebooking.airline_booking_system.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seats")
public class SeatController {

    private final SeatService seatService;

    @Autowired
    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create-seat")
    public ResponseEntity<SeatResponseDTO> createSeat(@RequestBody SeatRequestDTO seatRequestDTO) {
        SeatResponseDTO seatResponse = seatService.createSeat(seatRequestDTO);
        return ResponseEntity.ok(seatResponse);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'AGENT')")
    @GetMapping("/get-all-seats-by-flight/{flightNumber}")
    public ResponseEntity<List<SeatResponseDTO>> getAllSeatsByFlight(@PathVariable String flightNumber) {
        List<SeatResponseDTO> seats = seatService.getAllSeatsByFlight(flightNumber);
        return ResponseEntity.ok(seats);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'AGENT')")
    @GetMapping("/get-available-seats-by-flight/{flightNumber}")
    public ResponseEntity<List<SeatResponseDTO>> getAvailableSeatsByFlight(@PathVariable String flightNumber) {
        List<SeatResponseDTO> availableSeats = seatService.getAvailableSeatsByFlight(flightNumber);
        return ResponseEntity.ok(availableSeats);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'AGENT')")
    @PostMapping("/get-seat-details")
    public ResponseEntity<List<SeatResponseDTO>> getSeatDetails(
            @RequestParam String flightNumber,
            @RequestBody List<String> seatNumbers
    ) {
        List<SeatResponseDTO> seatDetails = seatService.getSeatDetails(flightNumber, seatNumbers);
        return ResponseEntity.ok(seatDetails);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'AGENT')")
    @PostMapping("/is-seat-available")
    public ResponseEntity<Boolean> isSeatAvailable(
            @RequestParam String flightNumber,
            @RequestBody List<String> seatNumbers
    ) {
        boolean isAvailable = seatService.isSeatAvailable(flightNumber, seatNumbers);
        return ResponseEntity.ok(isAvailable);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'AGENT')")
    @GetMapping("/count-available-seats")
    public ResponseEntity<Long> countAvailableSeats(@RequestParam String flightNumber) {
        long count = seatService.countAvailableSeats(flightNumber);
        return ResponseEntity.ok(count);
    }

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

