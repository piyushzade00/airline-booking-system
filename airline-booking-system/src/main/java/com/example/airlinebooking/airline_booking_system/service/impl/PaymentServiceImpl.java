package com.example.airlinebooking.airline_booking_system.service.impl;

import com.example.airlinebooking.airline_booking_system.dto.payment.PaymentRequestDTO;
import com.example.airlinebooking.airline_booking_system.dto.payment.PaymentResponseDTO;
import com.example.airlinebooking.airline_booking_system.entity.PaymentEntity;
import com.example.airlinebooking.airline_booking_system.entity.BookingEntity;
import com.example.airlinebooking.airline_booking_system.entity.UserEntity;
import com.example.airlinebooking.airline_booking_system.enums.PaymentMethod;
import com.example.airlinebooking.airline_booking_system.enums.PaymentStatus;
import com.example.airlinebooking.airline_booking_system.exception.ResourceNotFoundException;
import com.example.airlinebooking.airline_booking_system.mapper.PaymentMapper;
import com.example.airlinebooking.airline_booking_system.repository.BookingRepository;
import com.example.airlinebooking.airline_booking_system.repository.PaymentRepository;
import com.example.airlinebooking.airline_booking_system.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;
    private final PaymentMapper paymentMapper;

    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository, BookingRepository bookingRepository, PaymentMapper paymentMapper) {
        this.paymentRepository = paymentRepository;
        this.bookingRepository = bookingRepository;
        this.paymentMapper = paymentMapper;
    }

    @Override
    public PaymentResponseDTO processPayment(PaymentRequestDTO paymentRequestDTO) {
        String bookingCode = paymentRequestDTO.getBookingCode();

        BookingEntity bookingEntity = getBookingEntity(bookingCode);

        PaymentEntity paymentEntity = paymentMapper.toEntity(paymentRequestDTO, bookingEntity);
        PaymentEntity savedPayment = paymentRepository.savePaymentEntity(paymentEntity);
        return paymentMapper.toResponseDTO(savedPayment);
    }

    @Override
    public PaymentResponseDTO getPaymentById(Long paymentId) {
        if(paymentId == null || paymentId <= 0) {
            throw new ResourceNotFoundException("Payment id is empty");
        }
        PaymentEntity paymentEntity = paymentRepository.findPaymentEntityByPaymentId(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with ID: " + paymentId));
        return paymentMapper.toResponseDTO(paymentEntity);
    }

    @Override
    public List<PaymentResponseDTO> getPaymentsByStatus(PaymentStatus paymentStatus) {
        if(paymentStatus == null) {
            throw new ResourceNotFoundException("Payment status is empty");
        }
        return paymentRepository.findByPaymentStatus(paymentStatus)
                .stream()
                .map(paymentMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentResponseDTO> getPaymentsByBooking(String bookingCode) {
        BookingEntity booking = getBookingEntity(bookingCode);

        return paymentRepository.findByBooking(booking)
                .stream()
                .map(paymentMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentResponseDTO> getPaymentsByUser(UserEntity user) {
        if(user == null) {
            throw new ResourceNotFoundException("User is empty");
        }
        return paymentRepository.findByBooking_User(user)
                .stream()
                .map(paymentMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentResponseDTO> getPaymentsByBookingAndStatus(String bookingCode, PaymentStatus status) {
        if(status == null) {
            throw new ResourceNotFoundException("Payment status is empty");
        }

        BookingEntity booking = getBookingEntity(bookingCode);

        return paymentRepository.findByBookingAndPaymentStatus(booking, status)
                .stream()
                .map(paymentMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentResponseDTO> getPaymentsWithinDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        if(startDate == null || endDate == null) {
            throw new ResourceNotFoundException("Start date and end date is empty");
        }
        if(startDate.isAfter(endDate)) {
            throw new ResourceNotFoundException("Start date is after end date");
        }

        return paymentRepository.findByPaymentDateBetween(startDate, endDate)
                .stream()
                .map(paymentMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BigDecimal getTotalAmountPaidForBooking(String bookingCode) {
        BookingEntity booking = getBookingEntity(bookingCode);
        return paymentRepository.getTotalAmountPaid(booking);
    }

    @Override
    public boolean checkPaymentMethodExistsForBooking(String bookingCode, PaymentMethod paymentMethod) {
        if(paymentMethod == null) {
            throw new ResourceNotFoundException("Payment method is empty");
        }

        BookingEntity booking = getBookingEntity(bookingCode);

        return paymentRepository.existsByBookingAndPaymentMethod(booking, paymentMethod);
    }

    @Override
    public PaymentResponseDTO getMostRecentPaymentForBooking(String bookingCode) {
        BookingEntity booking = getBookingEntity(bookingCode);

        PaymentEntity paymentEntity = paymentRepository.findTopByBookingOrderByPaymentDateDesc(booking)
                .orElseThrow(() -> new ResourceNotFoundException("No payment found for this booking."));
        return paymentMapper.toResponseDTO(paymentEntity);
    }

    @Override
    public long countSuccessfulPayments() {
        return paymentRepository.countByPaymentStatus(PaymentStatus.COMPLETED);
    }

    @Override
    public List<PaymentResponseDTO> getPaymentsByMethodAndStatus(PaymentMethod paymentMethod, PaymentStatus status) {
        if(paymentMethod == null) {
            throw new ResourceNotFoundException("Payment method is empty");
        }
        return paymentRepository.findByPaymentMethodAndPaymentStatus(paymentMethod, status)
                .stream()
                .map(paymentMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public boolean deletePaymentByPaymentId(Long paymentId) {
        if(paymentId == null) {
            throw new ResourceNotFoundException("Payment id is empty");
        }
        if (!paymentRepository.existsById(paymentId)) {
            throw new ResourceNotFoundException("Payment not found with ID: " + paymentId);
        }
        return paymentRepository.deleteByPaymentId(paymentId);
    }

    public BookingEntity getBookingEntity(String bookingCode) {
        if(bookingCode == null || bookingCode.trim().isEmpty()) {
            throw new ResourceNotFoundException("Booking code is empty");
        }

        return bookingRepository.findByBookingCode(bookingCode)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with ID: " + bookingCode));

    }
}
