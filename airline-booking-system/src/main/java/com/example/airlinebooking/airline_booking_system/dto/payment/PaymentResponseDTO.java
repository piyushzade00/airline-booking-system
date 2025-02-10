package com.example.airlinebooking.airline_booking_system.dto.payment;

import com.example.airlinebooking.airline_booking_system.enums.PaymentMethod;
import com.example.airlinebooking.airline_booking_system.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponseDTO {

    private Long paymentId;
    private String bookingCode;
    private PaymentMethod paymentMethod;
    private BigDecimal amount;
    private LocalDateTime paymentDate;
    private PaymentStatus paymentStatus;
}