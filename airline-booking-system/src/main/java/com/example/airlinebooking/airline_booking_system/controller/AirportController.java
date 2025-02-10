package com.example.airlinebooking.airline_booking_system.controller;

import com.example.airlinebooking.airline_booking_system.dto.airport.AirportRequestDTO;
import com.example.airlinebooking.airline_booking_system.dto.airport.AirportResponseDTO;
import com.example.airlinebooking.airline_booking_system.service.AirportService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/airports")
public class AirportController {

    private final AirportService airportService;

    @Autowired
    public AirportController(AirportService airportService) {
        this.airportService = airportService;
    }

    @PostMapping("/add-airport")
    public ResponseEntity<AirportResponseDTO> addAirport(@Valid @RequestBody AirportRequestDTO airportRequestDTO) {
        AirportResponseDTO responseDTO = airportService.addAirport(airportRequestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/get-all-airports")
    public ResponseEntity<List<AirportResponseDTO>> getAllAirports() {
        List<AirportResponseDTO> airports = airportService.getAllAirports();
        return new ResponseEntity<>(airports, HttpStatus.OK);
    }

    @GetMapping("/get-airport-by-code/{airportCode}")
    public ResponseEntity<AirportResponseDTO> getAirportByCode(@PathVariable String airportCode) {
        AirportResponseDTO responseDTO = airportService.getAirportByCode(airportCode);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/get-airport-by-name/{airportName}")
    public ResponseEntity<AirportResponseDTO> getAirportByName(@PathVariable String airportName) {
        AirportResponseDTO responseDTO = airportService.getAirportByName(airportName);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/get-airport-by-location/{location}")
    public ResponseEntity<List<AirportResponseDTO>> getAirportsByLocation(@PathVariable String location) {
        List<AirportResponseDTO> airports = airportService.getAirportsByLocation(location);
        return new ResponseEntity<>(airports, HttpStatus.OK);
    }

    @PostMapping("/does-airport-exists/{airportCode}")
    public ResponseEntity<Boolean> doesAirportExist(@PathVariable String airportCode) {
        boolean exists = airportService.doesAirportExist(airportCode);
        return ResponseEntity.ok(exists);
    }

    @DeleteMapping("/delete-airport-by-code/{airportCode}")
    public ResponseEntity<Boolean> deleteAirportByCode(@PathVariable String airportCode) {
        boolean isDeleted = airportService.deleteAirportByCode(airportCode);
        return ResponseEntity.ok(isDeleted);
    }

    @DeleteMapping("/delete-airport-by-name/{airportName}")
    public ResponseEntity<Boolean> deleteAirportByName(@PathVariable String airportName) {
        boolean isDeleted = airportService.deleteAirportByName(airportName);
        return ResponseEntity.ok(isDeleted);
    }
}
