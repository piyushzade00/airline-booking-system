package com.example.airlinebooking.airline_booking_system.mapper;

import com.example.airlinebooking.airline_booking_system.dto.airport.AirportRequestDTO;
import com.example.airlinebooking.airline_booking_system.dto.airport.AirportResponseDTO;
import com.example.airlinebooking.airline_booking_system.entity.AirportEntity;
import org.springframework.stereotype.Component;

@Component
public class AirportMapper {

    public AirportEntity toEntity(AirportRequestDTO airportRequestDTO) {
        if (airportRequestDTO == null) {
            return null;
        }
        AirportEntity airportEntity = new AirportEntity();
        airportEntity.setAirportName(airportRequestDTO.getAirportName());
        airportEntity.setAirportCode(airportRequestDTO.getAirportCode());
        airportEntity.setLocation(airportRequestDTO.getLocation());
        return airportEntity;
    }

    public AirportResponseDTO toResponseDTO(AirportEntity airportEntity) {
        if (airportEntity == null) {
            return null;
        }
        return new AirportResponseDTO(
                airportEntity.getAirportId(),
                airportEntity.getAirportName(),
                airportEntity.getAirportCode(),
                airportEntity.getLocation()
        );
    }
}
