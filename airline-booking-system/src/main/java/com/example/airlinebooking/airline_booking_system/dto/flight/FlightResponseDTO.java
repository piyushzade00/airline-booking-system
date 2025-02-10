package com.example.airlinebooking.airline_booking_system.dto.flight;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightResponseDTO {

    private Long flightId;

    private String flightNumber;

    private String airlineName;

    private LocalDateTime departureTime;

    private LocalDateTime arrivalTime;

    private Long durationMinutes;

    private int totalSeats;

    private int availableSeats;

    private BigDecimal price;

    private String sourceAirportCode;

    private String destinationAirportCode;
}