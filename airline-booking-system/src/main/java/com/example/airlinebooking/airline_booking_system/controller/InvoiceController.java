package com.example.airlinebooking.airline_booking_system.controller;

import com.example.airlinebooking.airline_booking_system.service.InvoiceService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Invoice Management", description = "APIs for generating and sending invoices for bookings")
@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;

    @Autowired
    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @Operation(
            summary = "Generate and send invoice",
            description = "Generates an invoice for a given booking and sends it via email."
    )
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'AGENT')")
    @PostMapping("/generate-and-send")
    public ResponseEntity<String> generateAndSendInvoice(@RequestParam Long bookingId,
                                                         @RequestParam String email) {
        try {
            invoiceService.generateAndSendInvoice(bookingId, email);
            return ResponseEntity.ok("Invoice generated and sent successfully.");
        } catch (MessagingException e) {
            return ResponseEntity.internalServerError().body("Failed to send invoice: " + e.getMessage());
        }
    }
}
