package com.example.airlinebooking.airline_booking_system.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class SeatAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @After("execution(* com.example.airlinebooking.airline_booking_system.service.SeatService.allocateSeat(..))")
    public void logSeatAllocation(JoinPoint joinPoint) {
        logger.info("Seat allocated: " + Arrays.toString(joinPoint.getArgs()));
    }

}
