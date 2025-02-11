package com.example.airlinebooking.airline_booking_system.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class NotificationAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("execution(* com.example.airlinebooking.airline_booking_system.service.NotificationService.createNotification(..))")
    public Object retryNotification(ProceedingJoinPoint joinPoint) throws Throwable {
        int attempts = 0;
        int maxRetries = 3;
        while (attempts < maxRetries) {
            try {
                return joinPoint.proceed();
            } catch (Exception e) {
                attempts++;
                logger.warn("Notification failed. Retry attempt " + attempts);
                if (attempts == maxRetries) throw e;
            }
        }
        return null;
    }

}
