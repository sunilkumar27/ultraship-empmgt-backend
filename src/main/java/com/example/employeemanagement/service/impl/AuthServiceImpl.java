package com.example.employeemanagement.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.employeemanagement.dto.request.LoginRequest;
import com.example.employeemanagement.dto.response.JwtAuthenticationResponse;
import com.example.employeemanagement.exception.InvalidCredentialsException;
import com.example.employeemanagement.model.entity.Employee;
import com.example.employeemanagement.repository.EmployeeRepository;
import com.example.employeemanagement.security.JwtTokenProvider;
import com.example.employeemanagement.service.AuthService;

import lombok.RequiredArgsConstructor;

/**
 * Implementation of the AuthService interface.
 * Handles authentication and token management.
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final EmployeeRepository employeeRepository;
    
    private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);

    @Override
    @Transactional
    public JwtAuthenticationResponse login(LoginRequest loginRequest) {
        try {
        	log.debug("LoginRequest : " + loginRequest.toString());
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(),
                    loginRequest.getPassword()
                )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = tokenProvider.generateToken(authentication);
            log.debug("jwt : " + jwt);
            String refreshToken = tokenProvider.generateRefreshToken(authentication);
            log.debug("refreshToken : " + refreshToken);

            return JwtAuthenticationResponse.builder()
                    .accessToken(jwt)
                    .refreshToken(refreshToken)
                    .tokenType("Bearer")
                    .build();
        }  catch (UsernameNotFoundException e) {
            log.debug("Username not found: {}", loginRequest.getUsername());
            throw new InvalidCredentialsException("Invalid username or password");
        } catch (BadCredentialsException e) {
            log.debug("Bad credentials for user: {}", loginRequest.getUsername());
            throw new InvalidCredentialsException("Invalid username or password");
        } catch (AuthenticationException e) {
            log.error("Authentication failed: {}", e.getMessage());
            throw new InvalidCredentialsException("Invalid username or password");
        } catch (Exception e) {
            log.error("Authentication failed: {}", e.getMessage());
            throw new InvalidCredentialsException("Invalid username or password");
        }
    }

    @Override
    @Transactional
    public JwtAuthenticationResponse refreshToken(String refreshToken) {
        if (!tokenProvider.validateToken(refreshToken)) {
            throw new InvalidCredentialsException("Invalid refresh token");
        }

        String username = tokenProvider.getUsernameFromToken(refreshToken);
        Employee employee = employeeRepository.findByName(username)
                .orElseThrow(() -> new InvalidCredentialsException("User not found"));

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                employee,
                null,
                employee.getAuthorities()
        );

        String newAccessToken = tokenProvider.generateToken(authentication);
        String newRefreshToken = tokenProvider.generateRefreshToken(authentication);

        return JwtAuthenticationResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .tokenType("Bearer")
                .build();
    }
}