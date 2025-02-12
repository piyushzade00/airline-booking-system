package com.example.airlinebooking.airline_booking_system.service;

import com.example.airlinebooking.airline_booking_system.entity.BookingEntity;

public interface PDFService {

    String generateInvoice(BookingEntity bookingEntity);
}
