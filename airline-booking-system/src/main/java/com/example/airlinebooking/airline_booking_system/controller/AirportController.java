package com.example.airlinebooking.airline_booking_system.controller;

import com.example.airlinebooking.airline_booking_system.dto.airport.AirportRequestDTO;
import com.example.airlinebooking.airline_booking_system.dto.airport.AirportResponseDTO;
import com.example.airlinebooking.airline_booking_system.service.AirportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Airport Management", description = "APIs for managing airport details")
@RestController
@RequestMapping("/api/airports")
public class AirportController {

    private final AirportService airportService;

    @Autowired
    public AirportController(AirportService airportService) {
        this.airportService = airportService;
    }

    @Operation(summary = "Add a new airport", description = "Creates and returns a new airport (Admin only)")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add-airport")
    public ResponseEntity<AirportResponseDTO> addAirport(@Valid @RequestBody AirportRequestDTO airportRequestDTO) {
        AirportResponseDTO responseDTO = airportService.addAirport(airportRequestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @Operation(summary = "Get all airports", description = "Retrieves a list of all airports (Admin, Customer, Agent)")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'AGENT')")
    @GetMapping("/get-all-airports")
    public ResponseEntity<List<AirportResponseDTO>> getAllAirports() {
        List<AirportResponseDTO> airports = airportService.getAllAirports();
        return new ResponseEntity<>(airports, HttpStatus.OK);
    }

    @Operation(summary = "Get airport by code", description = "Retrieves details of an airport by its unique code")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'AGENT')")
    @GetMapping("/get-airport-by-code/{airportCode}")
    public ResponseEntity<AirportResponseDTO> getAirportByCode(@PathVariable String airportCode) {
        AirportResponseDTO responseDTO = airportService.getAirportByCode(airportCode);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @Operation(summary = "Get airport by name", description = "Retrieves an airport's details using its name")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'AGENT')")
    @GetMapping("/get-airport-by-name/{airportName}")
    public ResponseEntity<AirportResponseDTO> getAirportByName(@PathVariable String airportName) {
        AirportResponseDTO responseDTO = airportService.getAirportByName(airportName);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @Operation(summary = "Get airports by location", description = "Finds airports located in a specific city")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'AGENT')")
    @GetMapping("/get-airport-by-location/{location}")
    public ResponseEntity<List<AirportResponseDTO>> getAirportsByLocation(@PathVariable String location) {
        List<AirportResponseDTO> airports = airportService.getAirportsByLocation(location);
        return new ResponseEntity<>(airports, HttpStatus.OK);
    }

    @Operation(summary = "Check if an airport exists", description = "Returns true if an airport exists with the given code")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'AGENT')")
    @GetMapping("/does-airport-exists/{airportCode}")
    public ResponseEntity<Boolean> doesAirportExist(@PathVariable String airportCode) {
        boolean exists = airportService.doesAirportExist(airportCode);
        return ResponseEntity.ok(exists);
    }

    @Operation(summary = "Delete airport by code", description = "Deletes an airport using its code (Admin only)")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete-airport-by-code/{airportCode}")
    public ResponseEntity<Boolean> deleteAirportByCode(@PathVariable String airportCode) {
        boolean isDeleted = airportService.deleteAirportByCode(airportCode);
        return ResponseEntity.ok(isDeleted);
    }

    @Operation(summary = "Delete airport by name", description = "Deletes an airport using its name (Admin only)")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete-airport-by-name/{airportName}")
    public ResponseEntity<Boolean> deleteAirportByName(@PathVariable String airportName) {
        boolean isDeleted = airportService.deleteAirportByName(airportName);
        return ResponseEntity.ok(isDeleted);
    }
}
