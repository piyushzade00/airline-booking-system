package com.example.airlinebooking.airline_booking_system.controller;

import com.example.airlinebooking.airline_booking_system.dto.passenger.PassengerRequestDTO;
import com.example.airlinebooking.airline_booking_system.dto.passenger.PassengerResponseDTO;
import com.example.airlinebooking.airline_booking_system.service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/passengers")
public class PassengerController {

    private final PassengerService passengerService;

    @Autowired
    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @PostMapping
    public ResponseEntity<PassengerResponseDTO> addPassenger(@RequestBody PassengerRequestDTO passengerRequestDTO) {
        PassengerResponseDTO response = passengerService.addPassenger(passengerRequestDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-passengers-by-booking/{bookingCode}")
    public ResponseEntity<List<PassengerResponseDTO>> getPassengersByBooking(
            @PathVariable String bookingCode) {
        List<PassengerResponseDTO> passengers = passengerService.getPassengersByBooking(bookingCode);
        return ResponseEntity.ok(passengers);
    }

    @GetMapping("/get-passengers-by-name/{passengerFullName}")
    public ResponseEntity<List<PassengerResponseDTO>> getPassengersByName(
            @PathVariable String passengerFullName) {
        List<PassengerResponseDTO> passengers = passengerService.getPassengersByName(passengerFullName);
        return ResponseEntity.ok(passengers);
    }

    @GetMapping("/get-all-passengers")
    public ResponseEntity<List<PassengerResponseDTO>> getAllPassengers() {
        List<PassengerResponseDTO> passengers = passengerService.getAllPassengers();
        return ResponseEntity.ok(passengers);
    }

    @DeleteMapping("/delete-passenger-by-id/{passengerId}")
    public ResponseEntity<String> deletePassengerById(@PathVariable Long passengerId) {
        boolean isDeleted = passengerService.deletePassengerById(passengerId);
        return ResponseEntity.ok(isDeleted ? "Passenger deleted successfully." : "Passenger deletion failed.");
    }
}
