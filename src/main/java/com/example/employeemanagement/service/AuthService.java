package com.example.employeemanagement.service;

import com.example.employeemanagement.dto.request.LoginRequest;
import com.example.employeemanagement.dto.response.JwtAuthenticationResponse;

/**
 * Service interface for authentication-related operations.
 */
public interface AuthService {
    
    /**
     * Authenticates a user and generates JWT tokens.
     *
     * @param loginRequest The login credentials
     * @return JWT token response
     */
    JwtAuthenticationResponse login(LoginRequest loginRequest);

    /**
     * Refreshes an expired JWT token.
     *
     * @param refreshToken The refresh token
     * @return New JWT token response
     */
    JwtAuthenticationResponse refreshToken(String refreshToken);
}