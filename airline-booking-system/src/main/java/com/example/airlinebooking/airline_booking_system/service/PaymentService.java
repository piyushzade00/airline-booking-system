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

    List<PaymentResponseDTO> getPaymentsByBooking(String bookingCode);

    List<PaymentResponseDTO> getPaymentsByUser(UserEntity user);

    List<PaymentResponseDTO> getPaymentsByBookingAndStatus(String bookingCode, PaymentStatus status);

    List<PaymentResponseDTO> getPaymentsWithinDateRange(LocalDateTime startDate, LocalDateTime endDate);

    BigDecimal getTotalAmountPaidForBooking(String bookingCode);

    boolean checkPaymentMethodExistsForBooking(String bookingCode, PaymentMethod paymentMethod);

    PaymentResponseDTO getMostRecentPaymentForBooking(String bookingCode);

    long countSuccessfulPayments();

    List<PaymentResponseDTO> getPaymentsByMethodAndStatus(PaymentMethod paymentMethod, PaymentStatus status);

    boolean deletePaymentByPaymentId(Long paymentId);
}
