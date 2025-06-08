package com.example.employeemanagement.dto.response;

import lombok.Builder;
import lombok.Data;

/**
 * DTO for JWT authentication response.
 * Contains access token, refresh token, and token type.
 */
@Data
@Builder
public class JwtAuthenticationResponse {
    private String accessToken;
    private String refreshToken;
    private String tokenType;
}