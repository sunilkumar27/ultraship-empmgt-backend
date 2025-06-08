package com.example.employeemanagement.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Exception thrown when authentication credentials are invalid.
 */
public class InvalidCredentialsException extends AuthenticationException {
    public InvalidCredentialsException(String message) {
        super(message);
    }
}