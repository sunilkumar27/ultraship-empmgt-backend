package com.example.employeemanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.employeemanagement")
public class EmployeeManagementApplication {

    static {
        System.out.println(">> ENV CHECK: DATABASE_URL = " + System.getenv("DATABASE_URL"));
        System.out.println(">> ENV CHECK: SPRING_PROFILES_ACTIVE = " + System.getenv("SPRING_PROFILES_ACTIVE"));
    }

	public static void main(String[] args) {
		SpringApplication.run(EmployeeManagementApplication.class, args);
	}

}
