package com.example.airlinebooking.airline_booking_system.entity;

import com.example.airlinebooking.airline_booking_system.enums.SeatType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "seats",
        uniqueConstraints = @UniqueConstraint(columnNames = {"seat_number", "flight_id"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeatEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seat_id")
    private Long seatId;

    @NotNull(message = "Seat number is required.")
    @Column(name = "seat_number", nullable = false)
    @Pattern(regexp = "[A-Z]{1}[0-9]{1}",
            message = "Seat number must follow the pattern: A1, B2, etc.")
    private String seatNumber;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Seat type is required.")
    @Column(name = "seat_type", nullable = false)
    private SeatType seatType;

    @Column(name = "is_available", nullable = false)
    private boolean isAvailable;

    // Reference to Flight
    @NotNull(message = "Flight reference is required.")
    @ManyToOne
    @JoinColumn(name = "flight_id", nullable = false)
    private FlightEntity flight;
}