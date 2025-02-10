package com.example.airlinebooking.airline_booking_system.controller;

import com.example.airlinebooking.airline_booking_system.dto.payment.PaymentRequestDTO;
import com.example.airlinebooking.airline_booking_system.dto.payment.PaymentResponseDTO;
import com.example.airlinebooking.airline_booking_system.entity.UserEntity;
import com.example.airlinebooking.airline_booking_system.enums.PaymentMethod;
import com.example.airlinebooking.airline_booking_system.enums.PaymentStatus;
import com.example.airlinebooking.airline_booking_system.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/process-Payment")
    public ResponseEntity<PaymentResponseDTO> processPayment(@RequestBody PaymentRequestDTO paymentRequestDTO) {
        PaymentResponseDTO response = paymentService.processPayment(paymentRequestDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-payment-by-id/{paymentId}")
    public ResponseEntity<PaymentResponseDTO> getPaymentById(@PathVariable Long paymentId) {
        PaymentResponseDTO response = paymentService.getPaymentById(paymentId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-payment-by-status/{status}")
    public ResponseEntity<List<PaymentResponseDTO>> getPaymentsByStatus(@PathVariable PaymentStatus status) {
        List<PaymentResponseDTO> responses = paymentService.getPaymentsByStatus(status);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/get-payment-by-booking/{bookingCode}")
    public ResponseEntity<List<PaymentResponseDTO>> getPaymentsByBooking(@PathVariable String bookingCode) {
        List<PaymentResponseDTO> responses = paymentService.getPaymentsByBooking(bookingCode);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/get-payment-by-user")
    public ResponseEntity<List<PaymentResponseDTO>> getPaymentsByUser(@RequestBody UserEntity user) {
        List<PaymentResponseDTO> responses = paymentService.getPaymentsByUser(user);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/get-payment-by-booking-and-status")
    public ResponseEntity<List<PaymentResponseDTO>> getPaymentsByBookingAndStatus(
            @RequestParam String bookingCode, @RequestParam PaymentStatus status) {
        List<PaymentResponseDTO> responses = paymentService.getPaymentsByBookingAndStatus(bookingCode, status);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/get-payments-within-date-range")
    public ResponseEntity<List<PaymentResponseDTO>> getPaymentsWithinDateRange(
            @RequestParam LocalDateTime startDate, @RequestParam LocalDateTime endDate) {
        List<PaymentResponseDTO> responses = paymentService.getPaymentsWithinDateRange(startDate, endDate);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/total-amount-paid/{bookingCode}")
    public ResponseEntity<BigDecimal> getTotalAmountPaidForBooking(@PathVariable String bookingCode) {
        BigDecimal totalAmount = paymentService.getTotalAmountPaidForBooking(bookingCode);
        return ResponseEntity.ok(totalAmount);
    }

    @GetMapping("/method-exists")
    public ResponseEntity<Boolean> checkPaymentMethodExistsForBooking(
            @RequestParam String bookingCode, @RequestParam PaymentMethod paymentMethod) {
        boolean exists = paymentService.checkPaymentMethodExistsForBooking(bookingCode, paymentMethod);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/get-recent-payment/{bookingCode}")
    public ResponseEntity<PaymentResponseDTO> getMostRecentPaymentForBooking(@PathVariable String bookingCode) {
        PaymentResponseDTO response = paymentService.getMostRecentPaymentForBooking(bookingCode);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/count-success")
    public ResponseEntity<Long> countSuccessfulPayments() {
        long count = paymentService.countSuccessfulPayments();
        return ResponseEntity.ok(count);
    }

    @GetMapping("/get-payment-by-method-and-status")
    public ResponseEntity<List<PaymentResponseDTO>> getPaymentsByMethodAndStatus(
            @RequestParam PaymentMethod paymentMethod, @RequestParam PaymentStatus status) {
        List<PaymentResponseDTO> responses = paymentService.getPaymentsByMethodAndStatus(paymentMethod, status);
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/delete-payment/{paymentId}")
    public ResponseEntity<Boolean> deletePaymentByPaymentId(@PathVariable Long paymentId) {
        boolean deleted = paymentService.deletePaymentByPaymentId(paymentId);
        return ResponseEntity.ok(deleted);
    }
}
