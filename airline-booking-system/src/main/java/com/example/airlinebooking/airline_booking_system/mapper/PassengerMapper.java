package com.example.airlinebooking.airline_booking_system.mapper;

import com.example.airlinebooking.airline_booking_system.dto.passenger.PassengerRequestDTO;
import com.example.airlinebooking.airline_booking_system.dto.passenger.PassengerResponseDTO;
import com.example.airlinebooking.airline_booking_system.entity.BookingEntity;
import com.example.airlinebooking.airline_booking_system.entity.PassengerEntity;
import com.example.airlinebooking.airline_booking_system.entity.SeatEntity;
import org.springframework.stereotype.Component;

@Component
public class PassengerMapper {

    public PassengerEntity toEntity(PassengerRequestDTO passengerRequestDTO, BookingEntity booking, SeatEntity seat) {
        PassengerEntity passenger = new PassengerEntity();
        passenger.setPassengerFullName(passengerRequestDTO.getPassengerFullName());
        passenger.setGender(passengerRequestDTO.getGender());
        passenger.setAge(passengerRequestDTO.getAge());
        passenger.setBooking(booking);
        passenger.setSeat(seat);
        return passenger;
    }

    public PassengerResponseDTO toResponseDTO(PassengerEntity passenger) {
        return new PassengerResponseDTO(
                passenger.getPassengerId(),
                passenger.getPassengerFullName(),
                passenger.getGender(),
                passenger.getAge(),
                passenger.getBooking().getBookingCode(),
                passenger.getSeat() != null ? passenger.getSeat().getSeatNumber() : null
        );
    }
}