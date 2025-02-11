package com.example.airlinebooking.airline_booking_system.controller;

import com.example.airlinebooking.airline_booking_system.dto.auth.AuthRequestDTO;
import com.example.airlinebooking.airline_booking_system.dto.auth.AuthResponseDTO;
import com.example.airlinebooking.airline_booking_system.dto.user.UserRequestDTO;
import com.example.airlinebooking.airline_booking_system.dto.user.UserResponseDTO;
import com.example.airlinebooking.airline_booking_system.security.JwtUtil;
import com.example.airlinebooking.airline_booking_system.service.TokenBlacklistService;
import com.example.airlinebooking.airline_booking_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final TokenBlacklistService tokenBlacklistService;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager,
                                    JwtUtil jwtUtil,
                                    UserService userService,
                                    TokenBlacklistService tokenBlacklistService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.tokenBlacklistService = tokenBlacklistService;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'AGENT')")
    @PostMapping("/login")
    public AuthResponseDTO login(@RequestBody AuthRequestDTO authRequest) {
        try {
            // Authenticate the user
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            // Generate JWT token
            String jwtToken = jwtUtil.generateToken(userDetails);

            return new AuthResponseDTO(jwtToken, "Login successful");
        } catch (AuthenticationException ex) {
            throw new RuntimeException("Invalid username or password");
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'AGENT')")
    @PostMapping("/register")
    public UserResponseDTO register(@RequestBody UserRequestDTO userRequest) {
        return userService.createUser(userRequest);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'AGENT')")
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String tokenHeader) {
        if (tokenHeader == null || !tokenHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("Invalid token");
        }

        String token = tokenHeader.substring(7);
        long expirationTime = jwtUtil.extractExpiration(token).getTime() - System.currentTimeMillis();
        if (expirationTime > 0) {
            tokenBlacklistService.blacklistToken(token, expirationTime);
        }

        return ResponseEntity.ok("Logout successful");
    }
}
