package com.example.airlinebooking.airline_booking_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class AirlineBookingSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(AirlineBookingSystemApplication.class, args);
	}

}
