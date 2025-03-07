package com.example.airlinebooking.airline_booking_system.repository;

import com.example.airlinebooking.airline_booking_system.entity.BookingEntity;
import com.example.airlinebooking.airline_booking_system.entity.FlightEntity;
import com.example.airlinebooking.airline_booking_system.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<BookingEntity, Long> {

    BookingEntity saveBookingEntity(BookingEntity bookingEntity);

    Optional<BookingEntity> findBookingEntityByBookingId(Long id);

    Optional<BookingEntity> findByBookingCode(String bookingCode);

    List<BookingEntity> findByUser(UserEntity user);

    List<BookingEntity> findByFlight(FlightEntity flight);

    List<BookingEntity> findByFlightAndUser(FlightEntity flight, UserEntity user);

    List<BookingEntity> findByBookingTimeBetween(LocalDateTime start, LocalDateTime end);

    List<BookingEntity> findAllBookings(Pageable pageable);

    boolean deleteByBookingCode(String bookingCode);
}
