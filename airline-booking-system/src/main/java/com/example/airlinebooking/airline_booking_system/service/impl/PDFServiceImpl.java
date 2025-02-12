package com.example.airlinebooking.airline_booking_system.service.impl;

import com.example.airlinebooking.airline_booking_system.entity.BookingEntity;
import com.example.airlinebooking.airline_booking_system.service.PDFService;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class PDFServiceImpl implements PDFService {

    private static final String INVOICE_DIRECTORY = "invoices/";

    public String generateInvoice(BookingEntity bookingEntity) {
        String filePath = INVOICE_DIRECTORY + "Invoice_" + bookingEntity.getBookingId() + ".pdf";
        File directory = new File(INVOICE_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdirs(); // Create the invoices directory if not exists
        }

        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
                contentStream.newLineAtOffset(100, 700);
                contentStream.showText("Invoice");
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(100, 670);
                contentStream.showText("Booking ID: " + bookingEntity.getBookingId());
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(100, 650);
                contentStream.showText("Customer Name: " + bookingEntity.getUser().getDisplayName());
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(100, 630);
                contentStream.showText("Flight: " + bookingEntity.getFlight().getFlightNumber());
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(100, 630);
                contentStream.showText("Amount: $" + bookingEntity.getTotalPrice());
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(100, 610);
                contentStream.showText("Transaction Date: " + bookingEntity.getBookingDate());
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(100, 610);
                contentStream.showText("Booking Status: " + bookingEntity.getBookingStatus());
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(100, 610);
                contentStream.showText("Payment Status: " + bookingEntity.getPaymentStatus());
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(100, 610);
                contentStream.showText("Travel Date: " + bookingEntity.getTravelDate());
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(100, 610);
                contentStream.showText("Departure Time: " + bookingEntity.getFlight().getDepartureTime());
                contentStream.endText();
            }

            document.save(filePath);
            return filePath;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
