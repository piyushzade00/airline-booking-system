package com.example.airlinebooking.airline_booking_system.service;

import com.example.airlinebooking.airline_booking_system.dto.payment.PaymentRequestDTO;
import com.example.airlinebooking.airline_booking_system.dto.payment.PaymentResponseDTO;
import com.example.airlinebooking.airline_booking_system.entity.BookingEntity;
import com.example.airlinebooking.airline_booking_system.entity.UserEntity;
import com.example.airlinebooking.airline_booking_system.enums.PaymentMethod;
import com.example.airlinebooking.airline_booking_system.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface PaymentService {

    PaymentResponseDTO processPayment(PaymentRequestDTO paymentRequestDTO);

    PaymentResponseDTO getPaymentById(Long paymentId);

    List<PaymentResponseDTO> getPaymentsByStatus(PaymentStatus paymentStatus);

    List<PaymentResponseDTO> getPaymentsByBooking(PaymentRequestDTO paymentRequestDTO);

    List<PaymentResponseDTO> getPaymentsByUser(UserEntity user);

    List<PaymentResponseDTO> getPaymentsByBookingAndStatus(PaymentRequestDTO paymentRequestDTO, PaymentStatus status);

    List<PaymentResponseDTO> getPaymentsWithinDateRange(LocalDateTime startDate, LocalDateTime endDate);

    BigDecimal getTotalAmountPaidForBooking(PaymentRequestDTO paymentRequestDTO);

    boolean checkPaymentMethodExistsForBooking(PaymentRequestDTO paymentRequestDTO);

    PaymentResponseDTO getMostRecentPaymentForBooking(PaymentRequestDTO paymentRequestDTO);

    long countSuccessfulPayments();

    List<PaymentResponseDTO> getPaymentsByMethodAndStatus(PaymentRequestDTO paymentRequestDTO, PaymentStatus status);

    void deletePaymentById(Long paymentId);
}
