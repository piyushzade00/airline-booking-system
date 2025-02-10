package com.example.airlinebooking.airline_booking_system.mapper;

import com.example.airlinebooking.airline_booking_system.dto.payment.PaymentRequestDTO;
import com.example.airlinebooking.airline_booking_system.dto.payment.PaymentResponseDTO;
import com.example.airlinebooking.airline_booking_system.entity.BookingEntity;
import com.example.airlinebooking.airline_booking_system.entity.PaymentEntity;
import com.example.airlinebooking.airline_booking_system.enums.PaymentStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PaymentMapper {

    public PaymentEntity toEntity(PaymentRequestDTO requestDTO, BookingEntity booking) {
        if (requestDTO == null || booking == null) {
            throw new IllegalArgumentException("RequestDTO and booking entity cannot be null");
        }

        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setAmount(requestDTO.getAmount());
        paymentEntity.setPaymentMethod(requestDTO.getPaymentMethod());
        paymentEntity.setPaymentDate(LocalDateTime.now());
        paymentEntity.setPaymentStatus(PaymentStatus.PENDING); // Default status
        paymentEntity.setBooking(booking);

        return paymentEntity;
    }

    public PaymentResponseDTO toResponseDTO(PaymentEntity paymentEntity) {
        if (paymentEntity == null) {
            throw new IllegalArgumentException("Payment entity cannot be null");
        }

        return new PaymentResponseDTO(
                paymentEntity.getPaymentId(),
                paymentEntity.getBooking().getBookingCode(),
                paymentEntity.getPaymentMethod(),
                paymentEntity.getAmount(),
                paymentEntity.getPaymentDate(),
                paymentEntity.getPaymentStatus()
        );
    }
}