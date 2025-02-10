package com.example.airlinebooking.airline_booking_system.repository;

import com.example.airlinebooking.airline_booking_system.entity.BookingEntity;
import com.example.airlinebooking.airline_booking_system.entity.PaymentEntity;
import com.example.airlinebooking.airline_booking_system.entity.UserEntity;
import com.example.airlinebooking.airline_booking_system.enums.PaymentMethod;
import com.example.airlinebooking.airline_booking_system.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {

    PaymentEntity savePaymentEntity(PaymentEntity paymentEntity);

    Optional<PaymentEntity> findPaymentEntityByPaymentId(Long paymentId);

    // Find payments by status
    List<PaymentEntity> findByPaymentStatus(PaymentStatus paymentStatus);

    // Find payments by booking
    List<PaymentEntity> findByBooking(BookingEntity booking);

    List<PaymentEntity> findByBooking_User(UserEntity user);

    // Find successful payments for a specific booking
    List<PaymentEntity> findByBookingAndPaymentStatus(BookingEntity booking, PaymentStatus status);

    // Find payments by date range
    List<PaymentEntity> findByPaymentDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    // Get the total amount paid by a user for a booking
    @Query("SELECT SUM(p.amount) FROM PaymentEntity p WHERE p.booking = :booking AND p.paymentStatus = 'COMPLETED'")
    BigDecimal getTotalAmountPaid(BookingEntity booking);

    // Check if a payment with a specific method exists for a booking
    boolean existsByBookingAndPaymentMethod(BookingEntity booking, PaymentMethod paymentMethod);

    // Find the most recent payment for a booking
    Optional<PaymentEntity> findTopByBookingOrderByPaymentDateDesc(BookingEntity booking);

    // Count total successful payments
    long countByPaymentStatus(PaymentStatus status);

    // Find payments with specific methods and status
    List<PaymentEntity> findByPaymentMethodAndPaymentStatus(PaymentMethod paymentMethod, PaymentStatus status);
}
