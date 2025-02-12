package com.example.airlinebooking.airline_booking_system.controller;

import com.example.airlinebooking.airline_booking_system.dto.flight.FlightRequestDTO;
import com.example.airlinebooking.airline_booking_system.dto.flight.FlightResponseDTO;
import com.example.airlinebooking.airline_booking_system.service.FlightService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "Flight Management", description = "APIs for managing flights in the airline booking system")
@RestController
@RequestMapping("/api/flights")
public class FlightController {

    private final FlightService flightService;

    @Autowired
    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @Operation(summary = "Add a new flight", description = "Creates a new flight. Only admins can access this.")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add-flight")
    public ResponseEntity<FlightResponseDTO> createFlight(@Valid @RequestBody FlightRequestDTO flightRequestDTO) {
        FlightResponseDTO createdFlight = flightService.createFlight(flightRequestDTO);
        return ResponseEntity.ok(createdFlight);
    }

    @Operation(summary = "Get flight by flight number", description = "Retrieves a flight using its unique flight number.")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'AGENT')")
    @GetMapping("get-Flight-By-Number/{flightNumber}")
    public ResponseEntity<FlightResponseDTO> getFlightByNumber(@PathVariable String flightNumber) {
        FlightResponseDTO flight = flightService.getFlightByNumber(flightNumber);
        return ResponseEntity.ok(flight);
    }

    @Operation(summary = "Get flights by source and destination", description = "Finds flights between a specific source and destination.")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'AGENT')")
    @GetMapping("/get-flight-by-source-destination")
    public ResponseEntity<List<FlightResponseDTO>> getFlightsBySourceAndDestination(
            @RequestParam String source,
            @RequestParam String destination) {
        List<FlightResponseDTO> flights = flightService.getFlightsBySourceAndDestination(source, destination);
        return ResponseEntity.ok(flights);
    }

    @Operation(summary = "Get flights in a departure time range", description = "Retrieves flights that depart within a given time frame.")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'AGENT')")
    @GetMapping("/get-flight-in-departure-range")
    public ResponseEntity<List<FlightResponseDTO>> getFlightsWithinDepartureRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        List<FlightResponseDTO> flights = flightService.getFlightsWithinDepartureRange(start, end);
        return ResponseEntity.ok(flights);
    }

    @Operation(summary = "Get flights by source", description = "Finds flights originating from a specific source airport.")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'AGENT')")
    @GetMapping("/get-flight-by-source/{sourceCode}")
    public ResponseEntity<List<FlightResponseDTO>> getFlightsBySource(@PathVariable String sourceCode) {
        List<FlightResponseDTO> flights = flightService.getFlightsBySource(sourceCode);
        return ResponseEntity.ok(flights);
    }

    @Operation(summary = "Get flights by destination", description = "Finds flights arriving at a specific destination airport.")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'AGENT')")
    @GetMapping("/get-flight-by-destination/{destinationCode}")
    public ResponseEntity<List<FlightResponseDTO>> getFlightsByDestination(@PathVariable String destinationCode) {
        List<FlightResponseDTO> flights = flightService.getFlightsByDestination(destinationCode);
        return ResponseEntity.ok(flights);
    }

    @Operation(summary = "Get flights by airline", description = "Retrieves all flights operated by a specific airline.")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'AGENT')")
    @GetMapping("/get-flight-by-airline/{airlineName}")
    public ResponseEntity<List<FlightResponseDTO>> getFlightsByAirlineName(@PathVariable String airlineName) {
        List<FlightResponseDTO> flights = flightService.getFlightsByAirlineName(airlineName);
        return ResponseEntity.ok(flights);
    }

    @Operation(summary = "Get all flights", description = "Retrieves a list of all available flights.")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'AGENT')")
    @GetMapping("/get-all-flights")
    public ResponseEntity<List<FlightResponseDTO>> getAllFlights() {
        List<FlightResponseDTO> flights = flightService.getAllFlights();
        return ResponseEntity.ok(flights);
    }

    @Operation(summary = "Delete a flight by flight number", description = "Deletes a flight using its flight number. Only admins can access this.")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("delete-flight/{flightNumber}")
    public ResponseEntity<Boolean> deleteFlightByNumber(@PathVariable String flightNumber) {
        boolean isDeleted = flightService.deleteFlightByNumber(flightNumber);
        return ResponseEntity.ok(isDeleted);
    }
}

