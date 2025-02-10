package com.example.airlinebooking.airline_booking_system.mapper;

import com.example.airlinebooking.airline_booking_system.dto.booking.BookingRequestDTO;
import com.example.airlinebooking.airline_booking_system.dto.booking.BookingResponseDTO;
import com.example.airlinebooking.airline_booking_system.entity.BookingEntity;
import com.example.airlinebooking.airline_booking_system.entity.FlightEntity;
import com.example.airlinebooking.airline_booking_system.entity.UserEntity;
import com.example.airlinebooking.airline_booking_system.enums.BookingStatus;
import com.example.airlinebooking.airline_booking_system.enums.PaymentStatus;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class BookingMapper {
    public BookingEntity toEntity(BookingRequestDTO bookingRequestDTO,
                                         FlightEntity flightEntity,
                                         UserEntity userEntity) {
        if (bookingRequestDTO == null || flightEntity == null || userEntity == null) {
            return null;
        }
        BookingEntity bookingEntity = new BookingEntity();
        bookingEntity.setTravelDate(bookingRequestDTO.getTravelDate());
        bookingEntity.setNumberOfSeats(bookingRequestDTO.getNumberOfSeats());

        // Calculate total price based on seats and flight price
        BigDecimal seatPrice = flightEntity.getPrice();
        bookingEntity.setTotalPrice(seatPrice.multiply(BigDecimal.valueOf(bookingRequestDTO.getNumberOfSeats())));

        bookingEntity.setBookingDate(LocalDateTime.now());
        bookingEntity.setPaymentStatus(PaymentStatus.PENDING);
        bookingEntity.setBookingStatus(BookingStatus.CONFIRMED);
        bookingEntity.setFlight(flightEntity);
        bookingEntity.setUser(userEntity);
        return bookingEntity;
    }

    public BookingResponseDTO toResponseDTO(BookingEntity bookingEntity) {
        if (bookingEntity == null) {
            return null;
        }
        return new BookingResponseDTO(
                bookingEntity.getUser().getUserName(),
                bookingEntity.getBookingId(),
                bookingEntity.getBookingCode(),
                bookingEntity.getBookingDate(),
                bookingEntity.getTravelDate(),
                bookingEntity.getNumberOfSeats(),
                bookingEntity.getTotalPrice(),
                bookingEntity.getPaymentStatus(),
                bookingEntity.getBookingStatus(),
                bookingEntity.getFlight().getFlightNumber()
        );
    }
}
