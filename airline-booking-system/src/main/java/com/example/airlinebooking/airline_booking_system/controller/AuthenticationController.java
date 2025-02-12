package com.example.airlinebooking.airline_booking_system.controller;

import com.example.airlinebooking.airline_booking_system.dto.auth.AuthRequestDTO;
import com.example.airlinebooking.airline_booking_system.dto.auth.AuthResponseDTO;
import com.example.airlinebooking.airline_booking_system.dto.user.UserRequestDTO;
import com.example.airlinebooking.airline_booking_system.dto.user.UserResponseDTO;
import com.example.airlinebooking.airline_booking_system.security.JwtUtil;
import com.example.airlinebooking.airline_booking_system.service.TokenBlacklistService;
import com.example.airlinebooking.airline_booking_system.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Authentication", description = "APIs for user authentication and authorization")
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

    @Operation(summary = "User Login", description = "Authenticates user and returns a JWT token")
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody AuthRequestDTO authRequest) {
        try {
            // Authenticate the user
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            String jwtToken = jwtUtil.generateToken(userDetails);

            return ResponseEntity.ok(new AuthResponseDTO(jwtToken, "Login successful"));
        } catch (AuthenticationException ex) {
            return ResponseEntity.badRequest().body(new AuthResponseDTO(null, "Invalid username or password"));
        }
    }

    @Operation(summary = "User Registration", description = "Registers a new user")
    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@Valid @RequestBody UserRequestDTO userRequest) {
        UserResponseDTO userResponse = userService.createUser(userRequest);
        return ResponseEntity.ok(userResponse);
    }

    @Operation(summary = "User Logout", description = "Logs out the user by blacklisting the token")
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String tokenHeader) {
        if (!StringUtils.hasText(tokenHeader) || !tokenHeader.startsWith("Bearer ")) {
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
