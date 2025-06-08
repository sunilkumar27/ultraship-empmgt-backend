package com.example.employeemanagement;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGeneratorTest {
    
    @Test
    public void generatePassword() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = "admin";
        String encodedPassword = encoder.encode(password);
        
        System.out.println("\n---------- PASSWORD GENERATION ----------");
        System.out.println("Raw Password: " + password);
        System.out.println("Encoded Password: " + encodedPassword);
        System.out.println("Password Matches: " + encoder.matches(password, encodedPassword));
        System.out.println("----------------------------------------\n");
    }
}