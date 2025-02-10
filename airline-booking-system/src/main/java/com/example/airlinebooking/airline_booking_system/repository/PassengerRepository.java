package com.example.airlinebooking.airline_booking_system.repository;

import com.example.airlinebooking.airline_booking_system.entity.BookingEntity;
import com.example.airlinebooking.airline_booking_system.entity.PassengerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PassengerRepository extends JpaRepository<PassengerEntity, Long> {

    PassengerEntity savePassengerEntity(PassengerEntity passengerEntity);

    List<PassengerEntity> findPassengerByBooking(BookingEntity booking);

    List<PassengerEntity> findByPassengerFullName(String passengerFullName);

    List<PassengerEntity> findAllPassengers();

    boolean deleteByPassengerId(Long passengerId);

}
