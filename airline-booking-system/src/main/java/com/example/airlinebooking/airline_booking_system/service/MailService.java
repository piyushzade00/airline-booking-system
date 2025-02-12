package com.example.airlinebooking.airline_booking_system.service;

import jakarta.mail.MessagingException;

public interface MailService {

    void sendEmailWithAttachment(String to, String subject, String body, String attachmentPath) throws MessagingException;
}
