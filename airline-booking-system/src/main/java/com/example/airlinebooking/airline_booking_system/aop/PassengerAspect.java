package com.example.airlinebooking.airline_booking_system.aop;

import com.example.airlinebooking.airline_booking_system.dto.passenger.PassengerRequestDTO;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PassengerAspect {

    @Before("execution(* com.example.airlinebooking.airline_booking_system.service.PassengerService.createPassenger(..))")
    public void validatePassengerData(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        if (args[0] instanceof PassengerRequestDTO) {
            PassengerRequestDTO passenger = (PassengerRequestDTO) args[0];
            if (passenger.getPassengerFullName() == null || passenger.getPassengerFullName().isEmpty()) {
                throw new IllegalArgumentException("Passenger name is required");
            }
        }
    }

}
