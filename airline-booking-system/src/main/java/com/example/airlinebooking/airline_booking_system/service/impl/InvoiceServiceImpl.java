package com.example.airlinebooking.airline_booking_system.service.impl;

import com.example.airlinebooking.airline_booking_system.entity.BookingEntity;
import com.example.airlinebooking.airline_booking_system.repository.BookingRepository;
import com.example.airlinebooking.airline_booking_system.service.InvoiceService;
import com.example.airlinebooking.airline_booking_system.service.MailService;
import com.example.airlinebooking.airline_booking_system.service.PDFService;
import com.example.airlinebooking.airline_booking_system.service.S3Service;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@EnableAsync
@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final PDFService pdfService;
    private final MailService mailService;
    private final S3Service s3Service;
    private final BookingRepository bookingRepository;

    private final ExecutorService executorService = Executors.newFixedThreadPool(5);

    @Autowired
    public InvoiceServiceImpl(PDFService pdfService,
                              MailService mailService,
                              S3Service s3Service,
                              BookingRepository bookingRepository) {
        this.pdfService = pdfService;
        this.mailService = mailService;
        this.s3Service = s3Service;
        this.bookingRepository = bookingRepository;
    }

    @Async
    @Override
    public void generateAndSendInvoice(Long bookingId, String email) throws MessagingException {
        Future<String> future = executorService.submit(() -> generateInvoice(bookingId, email));

        try {
            String result = future.get(); // Wait for invoice generation to complete
            System.out.println(result);
        } catch (Exception e) {
            throw new RuntimeException("Error generating invoice: " + e.getMessage());
        }
    }

    private String generateInvoice(Long bookingId, String email) throws MessagingException {
        BookingEntity booking = bookingRepository.findBookingEntityByBookingId(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));



        String pdfPath = pdfService.generateInvoice(booking);

        if (pdfPath != null) {
            String s3FileName = "invoices/" + "Invoice_" + booking.getBookingId() + ".pdf";
            String preSignedUrl = s3Service.uploadFileToS3(pdfPath, s3FileName);

            if (preSignedUrl != null) {
                String subject = "Your Invoice - " + bookingId;
                String body = "Dear " +  booking.getUser().getDisplayName() + ",\n\nThank you for your payment. Your invoice is available at the following link:\n\n"
                        + preSignedUrl + "\n\nBest regards,\nAirline Booking System";

                mailService.sendEmailWithAttachment(email, subject, body, pdfPath);
                return "Invoice " + bookingId + " generated & email sent!";
            } else {
                throw new MessagingException("Failed to upload invoice to S3 for Invoice: " + bookingId);
            }
        }
        return "Invoice generation failed!";
    }
}
