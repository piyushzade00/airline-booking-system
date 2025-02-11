package com.example.airlinebooking.airline_booking_system.controller;

import com.example.airlinebooking.airline_booking_system.dto.flight.FlightRequestDTO;
import com.example.airlinebooking.airline_booking_system.dto.flight.FlightResponseDTO;
import com.example.airlinebooking.airline_booking_system.exception.ResourceNotFoundException;
import com.example.airlinebooking.airline_booking_system.service.FlightService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/flights")
public class FlightController {

    private final FlightService flightService;

    @Autowired
    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add-flight")
    public ResponseEntity<FlightResponseDTO> createFlight(@Valid @RequestBody FlightRequestDTO flightRequestDTO) {
        FlightResponseDTO createdFlight = flightService.createFlight(flightRequestDTO);
        return ResponseEntity.ok(createdFlight);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'AGENT')")
    @GetMapping("get-Flight-By-Number/{flightNumber}")
    public ResponseEntity<FlightResponseDTO> getFlightByNumber(@PathVariable String flightNumber) {
        FlightResponseDTO flight = flightService.getFlightByNumber(flightNumber);
        return ResponseEntity.ok(flight);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'AGENT')")
    @GetMapping("/get-flight-by-source-destination")
    public ResponseEntity<List<FlightResponseDTO>> getFlightsBySourceAndDestination(
            @RequestParam String source,
            @RequestParam String destination) {
        List<FlightResponseDTO> flights = flightService.getFlightsBySourceAndDestination(source, destination);
        return ResponseEntity.ok(flights);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'AGENT')")
    @GetMapping("/get-flight-in-departure-range")
    public ResponseEntity<List<FlightResponseDTO>> getFlightsWithinDepartureRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        List<FlightResponseDTO> flights = flightService.getFlightsWithinDepartureRange(start, end);
        return ResponseEntity.ok(flights);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'AGENT')")
    @GetMapping("/get-flight-by-source/{sourceCode}")
    public ResponseEntity<List<FlightResponseDTO>> getFlightsBySource(@PathVariable String sourceCode) {
        List<FlightResponseDTO> flights = flightService.getFlightsBySource(sourceCode);
        return ResponseEntity.ok(flights);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'AGENT')")
    @GetMapping("/get-flight-by-destination/{destinationCode}")
    public ResponseEntity<List<FlightResponseDTO>> getFlightsByDestination(@PathVariable String destinationCode) {
        List<FlightResponseDTO> flights = flightService.getFlightsByDestination(destinationCode);
        return ResponseEntity.ok(flights);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'AGENT')")
    @GetMapping("/get-flight-by-airline/{airlineName}")
    public ResponseEntity<List<FlightResponseDTO>> getFlightsByAirlineName(@PathVariable String airlineName) {
        List<FlightResponseDTO> flights = flightService.getFlightsByAirlineName(airlineName);
        return ResponseEntity.ok(flights);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'AGENT')")
    @GetMapping("/get-all-flights")
    public ResponseEntity<List<FlightResponseDTO>> getAllFlights() {
        List<FlightResponseDTO> flights = flightService.getAllFlights();
        return ResponseEntity.ok(flights);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("delete-flight/{flightNumber}")
    public ResponseEntity<Boolean> deleteFlightByNumber(@PathVariable String flightNumber) {
        boolean isDeleted = flightService.deleteFlightByNumber(flightNumber);
        return ResponseEntity.ok(isDeleted);
    }
}

