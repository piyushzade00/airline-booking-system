package com.example.airlinebooking.airline_booking_system.service;

public interface TokenBlacklistService {

    void blacklistToken(String token, long expirationTimeMillis);

    boolean isTokenBlacklisted(String token);
}
