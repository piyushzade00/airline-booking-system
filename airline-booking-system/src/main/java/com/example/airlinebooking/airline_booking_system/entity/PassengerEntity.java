package com.example.airlinebooking.airline_booking_system.entity;

import com.example.airlinebooking.airline_booking_system.enums.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="passengers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PassengerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="passenger_id")
    private Long passengerId;

    @NotNull(message = "Passenger full name cannot be null.")
    @Column(name = "full_name", nullable = false)
    private String passengerFullName;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Passenger gender cannot be null.")
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @Min(1)
    @NotNull(message = "Passenger age cannot be null.")
    @Column(name = "age", nullable = false)
    private int age;

    // Reference to Booking
    @ManyToOne
    @JoinColumn(name = "booking_id", nullable = false)
    private BookingEntity booking;

    // Reference to Seat
    @OneToOne
    @JoinColumn(name = "seat_id")
    private SeatEntity seat;
}
