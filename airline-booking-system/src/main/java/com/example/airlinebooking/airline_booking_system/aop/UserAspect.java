package com.example.airlinebooking.airline_booking_system.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class UserAspect {

    @Before("execution(* com.example.airlinebooking.airline_booking_system.service.UserService.deleteUser*(..))")
    public void checkAdminAccess(JoinPoint joinPoint) {
        String currentUserRole = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        if (!currentUserRole.contains("ADMIN")) {
            throw new SecurityException("Only admin can delete users.");
        }
    }

}
