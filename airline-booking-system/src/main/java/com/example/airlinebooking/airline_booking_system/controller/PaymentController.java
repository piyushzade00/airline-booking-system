package com.example.airlinebooking.airline_booking_system.controller;

import com.example.airlinebooking.airline_booking_system.dto.payment.PaymentRequestDTO;
import com.example.airlinebooking.airline_booking_system.dto.payment.PaymentResponseDTO;
import com.example.airlinebooking.airline_booking_system.entity.UserEntity;
import com.example.airlinebooking.airline_booking_system.enums.PaymentMethod;
import com.example.airlinebooking.airline_booking_system.enums.PaymentStatus;
import com.example.airlinebooking.airline_booking_system.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "Payment Management", description = "APIs for handling payments")
@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Operation(
            summary = "Process a payment",
            description = "Processes a new payment for a booking.")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'AGENT')")
    @PostMapping("/process-Payment")
    public ResponseEntity<PaymentResponseDTO> processPayment(@RequestBody PaymentRequestDTO paymentRequestDTO) {
        PaymentResponseDTO response = paymentService.processPayment(paymentRequestDTO);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get payment by ID", description = "Retrieves payment details by payment ID.")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'AGENT')")
    @GetMapping("/get-payment-by-id/{paymentId}")
    public ResponseEntity<PaymentResponseDTO> getPaymentById(@PathVariable Long paymentId) {
        PaymentResponseDTO response = paymentService.getPaymentById(paymentId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get payments by status", description = "Retrieves all payments with a specific status.")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'AGENT')")
    @GetMapping("/get-payment-by-status/{status}")
    public ResponseEntity<List<PaymentResponseDTO>> getPaymentsByStatus(@PathVariable PaymentStatus status) {
        List<PaymentResponseDTO> responses = paymentService.getPaymentsByStatus(status);
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Get payments by booking", description = "Retrieves all payments for a specific booking code.")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'AGENT')")
    @GetMapping("/get-payment-by-booking/{bookingCode}")
    public ResponseEntity<List<PaymentResponseDTO>> getPaymentsByBooking(@PathVariable String bookingCode) {
        List<PaymentResponseDTO> responses = paymentService.getPaymentsByBooking(bookingCode);
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Get payments by user", description = "Retrieves all payments made by a specific user.")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'AGENT')")
    @GetMapping("/get-payment-by-user")
    public ResponseEntity<List<PaymentResponseDTO>> getPaymentsByUser(@RequestBody UserEntity user) {
        List<PaymentResponseDTO> responses = paymentService.getPaymentsByUser(user);
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Get payments by booking and status", description = "Retrieves payments made for a booking and based on their status.")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'AGENT')")
    @GetMapping("/get-payment-by-booking-and-status")
    public ResponseEntity<List<PaymentResponseDTO>> getPaymentsByBookingAndStatus(
            @RequestParam String bookingCode, @RequestParam PaymentStatus status) {
        List<PaymentResponseDTO> responses = paymentService.getPaymentsByBookingAndStatus(bookingCode, status);
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Get payments within date range", description = "Retrieves all payments within a specific date range.")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'AGENT')")
    @GetMapping("/get-payments-within-date-range")
    public ResponseEntity<List<PaymentResponseDTO>> getPaymentsWithinDateRange(
            @RequestParam LocalDateTime startDate, @RequestParam LocalDateTime endDate) {
        List<PaymentResponseDTO> responses = paymentService.getPaymentsWithinDateRange(startDate, endDate);
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Get total amount paid", description = "Calculates the total amount paid for a booking.")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'AGENT')")
    @GetMapping("/total-amount-paid/{bookingCode}")
    public ResponseEntity<BigDecimal> getTotalAmountPaidForBooking(@PathVariable String bookingCode) {
        BigDecimal totalAmount = paymentService.getTotalAmountPaidForBooking(bookingCode);
        return ResponseEntity.ok(totalAmount);
    }

    @Operation(summary = "Check payment method exists", description = "Checks if a payment method was used for a booking.")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'AGENT')")
    @GetMapping("/method-exists")
    public ResponseEntity<Boolean> checkPaymentMethodExistsForBooking(
            @RequestParam String bookingCode, @RequestParam PaymentMethod paymentMethod) {
        boolean exists = paymentService.checkPaymentMethodExistsForBooking(bookingCode, paymentMethod);
        return ResponseEntity.ok(exists);
    }

    @Operation(summary = "Get recent payment for booking", description = "Retrieves the most recent payment for a booking.")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'AGENT')")
    @GetMapping("/get-recent-payment/{bookingCode}")
    public ResponseEntity<PaymentResponseDTO> getMostRecentPaymentForBooking(@PathVariable String bookingCode) {
        PaymentResponseDTO response = paymentService.getMostRecentPaymentForBooking(bookingCode);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Count successful payments", description = "Counts the total number of successful payments.")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/count-success")
    public ResponseEntity<Long> countSuccessfulPayments() {
        long count = paymentService.countSuccessfulPayments();
        return ResponseEntity.ok(count);
    }

    @Operation(summary = "Get payments by method and status", description = "Retrieves payments by payment method and status.")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'AGENT')")
    @GetMapping("/get-payment-by-method-and-status")
    public ResponseEntity<List<PaymentResponseDTO>> getPaymentsByMethodAndStatus(
            @RequestParam PaymentMethod paymentMethod, @RequestParam PaymentStatus status) {
        List<PaymentResponseDTO> responses = paymentService.getPaymentsByMethodAndStatus(paymentMethod, status);
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Delete a payment", description = "Deletes a payment by its ID.")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete-payment/{paymentId}")
    public ResponseEntity<Boolean> deletePaymentByPaymentId(@PathVariable Long paymentId) {
        boolean deleted = paymentService.deletePaymentByPaymentId(paymentId);
        return ResponseEntity.ok(deleted);
    }
}
