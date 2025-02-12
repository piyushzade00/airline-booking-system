package com.example.airlinebooking.airline_booking_system.repository;

import com.example.airlinebooking.airline_booking_system.entity.BookingEntity;
import com.example.airlinebooking.airline_booking_system.entity.PassengerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface PassengerRepository extends JpaRepository<PassengerEntity, Long> {

    PassengerEntity savePassengerEntity(PassengerEntity passengerEntity);

    Optional<PassengerEntity> findByBooking_TicketNumber(String ticketNumber);

    List<PassengerEntity> findPassengerByBooking(BookingEntity booking);

    List<PassengerEntity> findByBooking_Flight_FlightNumber(String flightNumber);

    List<PassengerEntity> findByPassengerFullName(String passengerFullName);

    List<PassengerEntity> findAllPassengers(Pageable pageable);

    boolean deleteByPassengerId(Long passengerId);

}
