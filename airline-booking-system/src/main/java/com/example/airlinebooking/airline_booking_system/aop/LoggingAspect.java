package com.example.airlinebooking.airline_booking_system.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    // Pointcut for all methods in service.impl package
    @Pointcut("execution(* com.example.airlinebooking.airline_booking_system.service.impl.*.*(..))")
    public void serviceMethods() {}

    // Before advice
    @Before("serviceMethods()")
    public void logBeforeMethod() {
        logger.info("A service method is about to execute...");
    }

    // After advice
    @After("serviceMethods()")
    public void logAfterMethod() {
        logger.info("A service method has executed.");
    }

    // Exception handling advice
    @AfterThrowing(pointcut = "serviceMethods()", throwing = "ex")
    public void logAfterException(Exception ex) {
        logger.error("An exception occurred: {}", ex.getMessage());
    }
}
