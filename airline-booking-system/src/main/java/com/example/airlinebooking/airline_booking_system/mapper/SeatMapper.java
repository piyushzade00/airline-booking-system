package com.example.airlinebooking.airline_booking_system.mapper;

import com.example.airlinebooking.airline_booking_system.dto.seat.SeatRequestDTO;
import com.example.airlinebooking.airline_booking_system.dto.seat.SeatResponseDTO;
import com.example.airlinebooking.airline_booking_system.entity.FlightEntity;
import com.example.airlinebooking.airline_booking_system.entity.SeatEntity;
import org.springframework.stereotype.Component;

@Component
public class SeatMapper {

    public SeatEntity toEntity(SeatRequestDTO seatRequestDTO, FlightEntity flight) {
        SeatEntity seatEntity = new SeatEntity();
        seatEntity.setSeatNumber(seatRequestDTO.getSeatNumbers().get(0));
        seatEntity.setSeatType(seatRequestDTO.getSeatType());
        seatEntity.setAvailable(true);  // Default value when creating a new seat
        seatEntity.setFlight(flight);
        return seatEntity;
    }

    public SeatResponseDTO toResponseDTO(SeatEntity seatEntity) {
        SeatResponseDTO responseDTO = new SeatResponseDTO();
        responseDTO.setSeatId(seatEntity.getSeatId());
        responseDTO.setSeatNumber(seatEntity.getSeatNumber());
        responseDTO.setSeatType(seatEntity.getSeatType());
        responseDTO.setAvailable(seatEntity.isAvailable());
        responseDTO.setFlightNumber(seatEntity.getFlight().getFlightNumber());  // Assuming flight has flightCode property
        return responseDTO;
    }
}