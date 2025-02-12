package com.example.airlinebooking.airline_booking_system.service;

import jakarta.mail.MessagingException;

public interface TicketService {

    void generateAndSendTicket(String ticketNumber) throws MessagingException;
}
