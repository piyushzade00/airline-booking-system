package com.example.airlinebooking.airline_booking_system.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class FlightAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @After("execution(* com.example.airlinebooking.airline_booking_system.service.FlightService.*(..))")
    public void auditFlightService(JoinPoint joinPoint) {
        logger.info("Flight service executed: " + joinPoint.getSignature());
    }

}
