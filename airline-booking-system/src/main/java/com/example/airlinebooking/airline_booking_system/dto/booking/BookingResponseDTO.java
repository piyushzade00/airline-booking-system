package com.example.airlinebooking.airline_booking_system.dto.booking;

import com.example.airlinebooking.airline_booking_system.enums.BookingStatus;
import com.example.airlinebooking.airline_booking_system.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponseDTO {

    private String userName;
    private Long bookingId;
    private String bookingCode;
    private LocalDateTime bookingDate;
    private LocalDateTime travelDate;
    private int numberOfSeats;
    private BigDecimal totalPrice;
    private PaymentStatus paymentStatus;
    private BookingStatus bookingStatus;
    private String flightNumber;
}