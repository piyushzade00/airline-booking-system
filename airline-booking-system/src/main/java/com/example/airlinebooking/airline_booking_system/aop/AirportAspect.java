package com.example.airlinebooking.airline_booking_system.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AirportAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(* com.example.airlinebooking.airline_booking_system.service.AirportService.*(..))")
    public void logAirportChanges(JoinPoint joinPoint) {
        logger.info("Airport service method called: " + joinPoint.getSignature().getName());
    }

}
