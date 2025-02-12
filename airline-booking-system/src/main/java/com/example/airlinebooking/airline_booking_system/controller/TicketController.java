package com.example.airlinebooking.airline_booking_system.controller;

import com.example.airlinebooking.airline_booking_system.service.TicketService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

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
