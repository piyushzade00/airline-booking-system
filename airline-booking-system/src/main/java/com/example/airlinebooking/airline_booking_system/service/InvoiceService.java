package com.example.airlinebooking.airline_booking_system.service;

import jakarta.mail.MessagingException;

public interface InvoiceService {

    void generateAndSendInvoice(Long bookingId, String email) throws MessagingException;
}
