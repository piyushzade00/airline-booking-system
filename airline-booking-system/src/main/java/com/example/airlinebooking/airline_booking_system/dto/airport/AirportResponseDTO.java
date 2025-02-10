package com.example.airlinebooking.airline_booking_system.dto.airport;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AirportResponseDTO {

    private Long airportId;
    private String airportName;
    private String airportCode;
    private String location;
}