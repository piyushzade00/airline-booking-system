package com.example.airlinebooking.airline_booking_system.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "airports")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AirportEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "airport_id")
    private Long airportId;

    @NotNull(message = "Airport name cannot be null")
    @Size(min = 3, max = 50, message = "Airport name must be between 3 and 50 characters")
    @Column(name = "airport_name", nullable = false)
    private String airportName;

    @NotNull(message = "Airport code cannot be null")
    @Pattern(regexp = "^[A-Z]{3}$", message = "Airport code must be exactly 3 uppercase letters")
    @Column(name = "airport_code", nullable = false, unique = true)
    private String airportCode;

    @NotNull(message = "Location cannot be null")
    @Size(min = 2, max = 100, message = "Location must be between 2 and 100 characters")
    @Column(name = "location", nullable = false)
    private String location;

    // Reference to Flights
    @OneToMany(mappedBy = "sourceAirport")
    private List<FlightEntity> departingFlights;

    @OneToMany(mappedBy = "destinationAirport")
    private List<FlightEntity> arrivingFlights;
}