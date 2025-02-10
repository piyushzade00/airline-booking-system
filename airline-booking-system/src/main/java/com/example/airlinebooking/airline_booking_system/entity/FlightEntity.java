package com.example.airlinebooking.airline_booking_system.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "flights")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "flight_id")
    private Long flightId;

    @NotNull(message = "Flight number is required.")
    @Column(name="flight_number", nullable = false, unique = true)
    private String flightNumber;

    @NotNull(message = "Airline name is required.")
    @Column(name="airline_name", nullable = false)
    private String airlineName;

    @NotNull(message = "Departure time is required.")
    @Column(name = "departure_time",nullable = false)
    private LocalDateTime departureTime;

    @NotNull(message = "Arrival time is required.")
    @Column(name = "arrival_time",nullable = false)
    private LocalDateTime arrivalTime;

    @Transient
    private Long durationMinutes;

    @Column(name="total_seats", nullable = false)
    @Positive(message = "Total seats must be positive.")
    private int totalSeats;

    @Column(name="available_seats", nullable = false)
    @Positive(message = "Available seats must be positive.")
    private int availableSeats;

    @Column(precision = 10, scale = 2, nullable = false)
    @Positive(message = "Price must be a positive value.")
    private BigDecimal price;

    // Reference to Source Airport
    @NotNull(message = "Source airport cannot be null.")
    @ManyToOne
    @JoinColumn(name = "source_airport_id", nullable = false)
    private AirportEntity sourceAirportEntity;

    // Reference to Destination Airport
    @NotNull(message = "Destination airport cannot be null.")
    @ManyToOne
    @JoinColumn(name = "destination_airport_id", nullable = false)
    private AirportEntity destinationAirportEntity;

    public Long getDurationMinutes() {
        return (departureTime != null && arrivalTime != null) ?
                java.time.Duration.between(departureTime, arrivalTime).toMinutes() : null;
    }
}
