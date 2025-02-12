package com.example.airlinebooking.airline_booking_system.service.impl;

import com.example.airlinebooking.airline_booking_system.entity.PassengerEntity;
import com.example.airlinebooking.airline_booking_system.repository.PassengerRepository;
import com.example.airlinebooking.airline_booking_system.service.MailService;
import com.example.airlinebooking.airline_booking_system.service.S3Service;
import com.example.airlinebooking.airline_booking_system.service.TicketPDFService;
import com.example.airlinebooking.airline_booking_system.service.TicketService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
@EnableAsync
public class TicketServiceImpl implements TicketService {

    private final TicketPDFService ticketPDFService;
    private final MailService mailService;
    private final S3Service s3Service;
    private final PassengerRepository passengerRepository;

    private final ExecutorService executorService = Executors.newFixedThreadPool(5); // Process 5 tickets in parallel

    @Autowired
    public TicketServiceImpl(TicketPDFService ticketPDFService,
                             MailService mailService,
                             S3Service s3Service,
                             PassengerRepository passengerRepository) {
        this.ticketPDFService = ticketPDFService;
        this.mailService = mailService;
        this.s3Service = s3Service;
        this.passengerRepository = passengerRepository;
    }

    @Async
    public void generateAndSendTicket(String ticketNumber) throws MessagingException {
        Future<String> future = executorService.submit(() -> generateTicket(ticketNumber));

        try {
            String result = future.get(); // Wait for ticket generation to complete
            System.out.println(result);
        } catch (Exception e) {
            throw new RuntimeException("Error generating ticket: " + e.getMessage());
        }
    }

    private String generateTicket(String ticketNumber) throws MessagingException {
        PassengerEntity passenger = passengerRepository.findByBooking_TicketNumber(ticketNumber)
                .orElseThrow(() -> new RuntimeException("Passenger with ticket number " + ticketNumber + " not found."));

        String pdfPath = ticketPDFService.generateTicket(ticketNumber);

        if (pdfPath != null) {
            String s3FileName = "tickets/Ticket_" + ticketNumber + ".pdf";
            String preSignedUrl = s3Service.uploadFileToS3(pdfPath, s3FileName);

            if (preSignedUrl != null) {
                String subject = "Your Flight Ticket - " + passenger.getBooking().getFlight().getFlightNumber();
                String body = "Dear " + passenger.getPassengerFullName() + ",\n\n"
                        + "Your flight ticket is available at the following link:\n\n"
                        + preSignedUrl + "\n\nSafe travels!\nAirline Booking System";

                mailService.sendEmailWithAttachment(passenger.getBooking().getBookingId().toString(), subject, body, pdfPath);
                return "Ticket " + ticketNumber + " generated & email sent!";
            } else {
                throw new MessagingException("Failed to upload ticket to S3 for Ticket: " + ticketNumber);
            }
        }
        return "Ticket generation failed!";
    }
}
