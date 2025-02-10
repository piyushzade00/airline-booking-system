package com.example.airlinebooking.airline_booking_system.dto.payment;

import com.example.airlinebooking.airline_booking_system.enums.PaymentMethod;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequestDTO {

    @NotNull(message = "Booking code is required.")
    private String bookingCode;

    @NotNull(message = "Payment method is required.")
    private PaymentMethod paymentMethod;

    @NotNull(message = "Amount is required.")
    @Positive(message = "Amount must be positive.")
    private BigDecimal amount;
}