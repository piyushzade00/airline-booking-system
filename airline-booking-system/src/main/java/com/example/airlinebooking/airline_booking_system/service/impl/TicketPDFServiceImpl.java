package com.example.airlinebooking.airline_booking_system.service.impl;

import com.example.airlinebooking.airline_booking_system.entity.PassengerEntity;
import com.example.airlinebooking.airline_booking_system.repository.PassengerRepository;
import com.example.airlinebooking.airline_booking_system.service.TicketPDFService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;

@Service
public class TicketPDFServiceImpl implements TicketPDFService {

    private final PassengerRepository passengerRepository;

    @Autowired
    public TicketPDFServiceImpl(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    @Override
    public String generateTicket(String ticketNumber) {

        PassengerEntity passenger = passengerRepository.findByBooking_TicketNumber(ticketNumber)
                .orElseThrow(() -> new IllegalArgumentException("Passenger Not Found"));

        File ticketDir = new File("tickets");
        if (!ticketDir.exists()) {
            ticketDir.mkdirs();
        }

        String filePath = "tickets/Ticket_" + ticketNumber + ".pdf";
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Font normalFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);

            document.add(new Paragraph("Airline Boarding Pass", titleFont));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Ticket Number: " + ticketNumber, normalFont));
            document.add(new Paragraph("Passenger Name: " + passenger.getPassengerFullName(), normalFont));
            document.add(new Paragraph("Flight Number: " + passenger.getBooking().getFlight().getFlightNumber(), normalFont));
            document.add(new Paragraph("From: " + passenger.getBooking().getFlight().getSourceAirportEntity().getAirportName(), normalFont));
            document.add(new Paragraph("To: " + passenger.getBooking().getFlight().getDestinationAirportEntity().getAirportName(), normalFont));
            document.add(new Paragraph("Departure Date: " + passenger.getBooking().getFlight().getDepartureTime(), normalFont));
            document.add(new Paragraph("Seat Number: " + (passenger.getSeat() != null ? passenger.getSeat().getSeatNumber() : "Not Assigned"), normalFont));

            document.close();
            return filePath;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
