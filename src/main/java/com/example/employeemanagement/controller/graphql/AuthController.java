package com.example.employeemanagement.controller.graphql;

import com.example.employeemanagement.dto.request.LoginRequest;
import com.example.employeemanagement.dto.response.JwtAuthenticationResponse;
import com.example.employeemanagement.service.AuthService;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class AuthController {
	private static final Logger log = LoggerFactory.getLogger(AuthController.class);
	
    private final AuthService authService;

    @MutationMapping(name = "login")
    public JwtAuthenticationResponse login(@Argument LoginRequest input) {
    	log.debug("LoginRequest : " + input.toString());
        return authService.login(input);
    }

    @MutationMapping(name = "refreshToken")
    public JwtAuthenticationResponse refreshToken(@Argument String token) {
        return authService.refreshToken(token);
    }
}