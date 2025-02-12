package com.example.airlinebooking.airline_booking_system.controller;

import com.example.airlinebooking.airline_booking_system.service.MailService;
import com.example.airlinebooking.airline_booking_system.service.PassengerListExcelService;
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

@Tag(name = "Passenger List Management", description = "APIs for exporting passenger lists")
@RestController
@RequestMapping("/api/passenger-list")
public class PassengerListController {

    private final PassengerListExcelService passengerListExcelService;
    private final MailService mailService;

    @Autowired
    public PassengerListController(PassengerListExcelService passengerListExcelService, MailService mailService) {
        this.passengerListExcelService = passengerListExcelService;
        this.mailService = mailService;
    }

    @Operation(summary = "Export passenger list and send via email",
            description = "Generates an Excel file containing the passenger list for a specific flight and emails it to the airline.")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/export")
    public ResponseEntity<String> exportPassengerList(@RequestParam String flightNumber,
                                                      @RequestParam String airlineEmail) throws MessagingException {

        String preSignedUrl = passengerListExcelService.generatePassengerListExcel(flightNumber);
        if (preSignedUrl != null) {
            String subject = "Passenger List for Flight " + flightNumber;
            String body = "The passenger list is available at:\n" + preSignedUrl;
            mailService.sendEmailWithAttachment(airlineEmail, subject, body, preSignedUrl);
            return ResponseEntity.ok("Passenger list exported and sent successfully.");
        } else {
            return ResponseEntity.internalServerError().body("Failed to generate passenger list.");
        }
    }
}
