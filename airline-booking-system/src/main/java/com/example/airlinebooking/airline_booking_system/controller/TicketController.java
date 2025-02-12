package com.example.airlinebooking.airline_booking_system.controller;

import com.example.airlinebooking.airline_booking_system.service.TicketService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Ticket Management", description = "APIs for managing flight tickets")
@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @Operation(summary = "Generate and send ticket",
            description = "Generates a flight ticket and sends it via email to the user."
    )
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'AGENT')")
    @PostMapping("/generate-and-send")
    public ResponseEntity<String> generateAndSendTicket(@RequestParam String ticketNumber) {
        try {
            ticketService.generateAndSendTicket(ticketNumber);
            return ResponseEntity.ok("Ticket generated and sent successfully.");
        } catch (MessagingException e) {
            return ResponseEntity.internalServerError().body("Failed to send ticket: " + e.getMessage());
        }
    }
}
