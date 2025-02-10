package com.example.airlinebooking.airline_booking_system.entity;

import com.example.airlinebooking.airline_booking_system.enums.BookingStatus;
import com.example.airlinebooking.airline_booking_system.enums.PaymentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "bookings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private Long bookingId;

    @NotNull(message = "Booking code is required.")
    @Column(name = "booking_code", nullable = false, unique = true, updatable = false)
    private String bookingCode;

    @NotNull(message = "Booking date is required.")
    @Column(name = "booking_date", nullable = false)
    private LocalDateTime bookingDate;

    @Future(message = "Travel date must be in the future.")
    @NotNull(message = "Travel date is required.")
    @Column(name = "travel_date", nullable = false)
    private LocalDateTime travelDate;

    @Positive(message = "Number of seats must be a positive value.")
    @NotNull(message = "Number of seats is required.")
    @Column(nullable = false)
    private int numberOfSeats;

    @Positive(message = "Total price must be a positive value.")
    @NotNull(message = "Total price is required.")
    @Column(nullable = false)
    private BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Payment status is required.")
    @Column(name = "payment_status", nullable = false)
    private PaymentStatus paymentStatus;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Booking status is required.")
    @Column(name = "booking_status", nullable = false)
    private BookingStatus bookingStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flight_id", nullable = false)
    @NotNull(message = "Flight reference is required.")
    private FlightEntity flight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull(message = "User reference is required.")
    private UserEntity user;

    @PrePersist
    private void generateBookingCode() {
        this.bookingCode = "BOOK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
