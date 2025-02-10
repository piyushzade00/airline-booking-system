package com.example.airlinebooking.airline_booking_system.repository;

import com.example.airlinebooking.airline_booking_system.entity.AirportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AirportRepository extends JpaRepository<AirportEntity, Long> {

    AirportEntity saveAirportEntity(AirportEntity airportEntity);

    Optional<AirportEntity> findByAirportCode(String airportCode);

    Optional<AirportEntity> findByAirportName(String airportName);

    List<AirportEntity> findByLocation(String location);

    List<AirportEntity> findAllAirports();

    boolean existsByAirportCode(String airportCode);

    boolean deleteByAirportCode(String airportCode);

    boolean deleteByAirportName(String airportName);
}
