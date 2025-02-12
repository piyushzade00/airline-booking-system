package com.example.airlinebooking.airline_booking_system.controller;

import com.example.airlinebooking.airline_booking_system.dto.passenger.PassengerRequestDTO;
import com.example.airlinebooking.airline_booking_system.dto.passenger.PassengerResponseDTO;
import com.example.airlinebooking.airline_booking_system.service.PassengerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.awt.print.Pageable;
import java.util.List;

@Tag(name = "Passenger Management", description = "APIs for managing passengers")
@RestController
@RequestMapping("/api/passengers")
public class PassengerController {

    private final PassengerService passengerService;

    @Autowired
    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @Operation(summary = "Add a new passenger",
            description = "Adds a new passenger to a booking")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'AGENT')")
    @PostMapping
    public ResponseEntity<PassengerResponseDTO> addPassenger(@Valid @RequestBody PassengerRequestDTO passengerRequestDTO) {
        PassengerResponseDTO response = passengerService.addPassenger(passengerRequestDTO);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get passengers by booking code",
            description = "Retrieves a list of passengers associated with a specific booking")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'AGENT')")
    @GetMapping("/get-passengers-by-booking/{bookingCode}")
    public ResponseEntity<List<PassengerResponseDTO>> getPassengersByBooking(
            @PathVariable String bookingCode) {
        List<PassengerResponseDTO> passengers = passengerService.getPassengersByBooking(bookingCode);
        return ResponseEntity.ok(passengers);
    }

    @Operation(summary = "Get passengers by full name",
            description = "Retrieves passengers by their full name")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'AGENT')")
    @GetMapping("/get-passengers-by-name/{passengerFullName}")
    public ResponseEntity<List<PassengerResponseDTO>> getPassengersByName(
            @PathVariable String passengerFullName) {
        List<PassengerResponseDTO> passengers = passengerService.getPassengersByName(passengerFullName);
        return ResponseEntity.ok(passengers);
    }

    @Operation(summary = "Get all passengers",
            description = "Retrieves a list of all passengers")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'AGENT')")
    @GetMapping("/get-all-passengers")
    public ResponseEntity<List<PassengerResponseDTO>> getAllPassengers(Pageable pageable) {
        List<PassengerResponseDTO> passengers = passengerService.getAllPassengers(pageable);
        return ResponseEntity.ok(passengers);
    }

    @Operation( summary = "Delete a passenger by ID",
            description = "Deletes a passenger record using their ID")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'AGENT')")
    @DeleteMapping("/delete-passenger-by-id/{passengerId}")
    public ResponseEntity<Void> deletePassengerById(@PathVariable Long passengerId) {
        boolean isDeleted = passengerService.deletePassengerById(passengerId);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
