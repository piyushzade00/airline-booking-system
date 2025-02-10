package com.example.airlinebooking.airline_booking_system.controller;

import com.example.airlinebooking.airline_booking_system.dto.seat.SeatRequestDTO;
import com.example.airlinebooking.airline_booking_system.dto.seat.SeatResponseDTO;
import com.example.airlinebooking.airline_booking_system.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    // Create a new seat
    @PostMapping("/create-seat")
    public ResponseEntity<SeatResponseDTO> createSeat(@RequestBody SeatRequestDTO seatRequestDTO) {
        SeatResponseDTO seatResponse = seatService.createSeat(seatRequestDTO);
        return ResponseEntity.ok(seatResponse);
    }

    // Get all seats for a specific flight
    @GetMapping("/get-all-seats-by-flight/{flightNumber}")
    public ResponseEntity<List<SeatResponseDTO>> getAllSeatsByFlight(@PathVariable String flightNumber) {
        List<SeatResponseDTO> seats = seatService.getAllSeatsByFlight(flightNumber);
        return ResponseEntity.ok(seats);
    }

    // Get available seats for a specific flight
    @GetMapping("/get-available-seats-by-flight/{flightNumber}")
    public ResponseEntity<List<SeatResponseDTO>> getAvailableSeatsByFlight(@PathVariable String flightNumber) {
        List<SeatResponseDTO> availableSeats = seatService.getAvailableSeatsByFlight(flightNumber);
        return ResponseEntity.ok(availableSeats);
    }

    // Get seat details by seat numbers
    @PostMapping("/get-seat-details")
    public ResponseEntity<List<SeatResponseDTO>> getSeatDetails(
            @RequestParam String flightNumber,
            @RequestBody List<String> seatNumbers
    ) {
        List<SeatResponseDTO> seatDetails = seatService.getSeatDetails(flightNumber, seatNumbers);
        return ResponseEntity.ok(seatDetails);
    }

    // Check if a seat is available
    @PostMapping("/is-seat-available")
    public ResponseEntity<Boolean> isSeatAvailable(
            @RequestParam String flightNumber,
            @RequestBody List<String> seatNumbers
    ) {
        boolean isAvailable = seatService.isSeatAvailable(flightNumber, seatNumbers);
        return ResponseEntity.ok(isAvailable);
    }

    // Count available seats for a flight
    @GetMapping("/count-available-seats")
    public ResponseEntity<Long> countAvailableSeats(@RequestParam String flightNumber) {
        long count = seatService.countAvailableSeats(flightNumber);
        return ResponseEntity.ok(count);
    }

    // Mark specific seats as unavailable
    @PutMapping("/mark-seats-unavailable")
    public ResponseEntity<Void> markSeatsAsUnavailable(
            @RequestParam String flightNumber,
            @RequestBody List<String> seatNumbers
    ) {
        seatService.markSeatsAsUnavailable(flightNumber, seatNumbers);
        return ResponseEntity.noContent().build();
    }
}

