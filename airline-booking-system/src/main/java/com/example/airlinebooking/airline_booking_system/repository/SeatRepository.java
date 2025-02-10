package com.example.airlinebooking.airline_booking_system.repository;

import com.example.airlinebooking.airline_booking_system.entity.FlightEntity;
import com.example.airlinebooking.airline_booking_system.entity.SeatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeatRepository extends JpaRepository<SeatEntity, Long> {

    SeatEntity saveSeatEntity(SeatEntity seatEntity);

    // Find all seats for a given flight
    List<SeatEntity> findAllSeatsByFlight(FlightEntity flight);

    // Find available seats for a specific flight
    List<SeatEntity> findByFlightAndIsAvailableTrue(FlightEntity flight);

    // Find a specific seat by seat number and flight
    Optional<SeatEntity> findByFlightAndSeatNumber(FlightEntity flight, String seatNumber);

    // Check if a specific seat is available
    boolean existsByFlightAndSeatNumberAndIsAvailableTrue(FlightEntity flight, String seatNumber);

    // Count total available seats for a flight
    long countByFlightAndIsAvailableTrue(FlightEntity flight);

    @Modifying
    @Transactional
    @Query("UPDATE SeatEntity s SET s.isAvailable = false WHERE s.flight = :flight AND s.seatNumber IN :seatNumbers")
    void markSeatsAsUnavailable(FlightEntity flight, List<String> seatNumbers);
}
