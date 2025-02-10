package com.example.airlinebooking.airline_booking_system.repository;

import com.example.airlinebooking.airline_booking_system.entity.AirportEntity;
import com.example.airlinebooking.airline_booking_system.entity.FlightEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface FlightRepository extends JpaRepository<FlightEntity, Long> {

    FlightEntity saveFlightEntity(FlightEntity flightEntity);

    Optional<FlightEntity> findFlightEntityByFlightNumber(String flightNumber);

    List<FlightEntity> findBySourceAirportEntityAndDestinationAirportEntity(AirportEntity source, AirportEntity destination);

    List<FlightEntity> findByDepartureTimeBetween(LocalDateTime start, LocalDateTime end);

    List<FlightEntity> findBySourceAirportEntity(AirportEntity source);

    List<FlightEntity> findByDestinationAirportEntity(AirportEntity destination);

    List<FlightEntity> findByAirlineName(String airlineName);

    List<FlightEntity> findAllFlights();

    List<FlightEntity> findBySourceAirportEntityAndDestinationAirportEntityAndDepartureTimeBetween(
            AirportEntity source,
            AirportEntity destination,
            LocalDateTime start,
            LocalDateTime end
    );

    void deleteByFlightNumber(String flightNumber);

}
