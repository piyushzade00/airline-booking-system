package com.example.airlinebooking.airline_booking_system.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class BookingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @AfterThrowing(pointcut = "execution(* com.example.airlinebooking.airline_booking_system.service.BookingService.*(..))", throwing = "exception")
    public void handleBookingFailure(Exception exception) {
        logger.error("Booking failed: " + exception.getMessage());
    }

    @Around("execution(* com.example.airlinebooking.airline_booking_system.service.impl.BookingServiceImpl.*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long elapsedTime = System.currentTimeMillis() - start;
        logger.info("Method {} executed in {} ms", joinPoint.getSignature(), elapsedTime);
        return result;
    }

}
