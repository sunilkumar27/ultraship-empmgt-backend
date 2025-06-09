package com.example.employeemanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.employeemanagement")
public class EmployeeManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeManagementApplication.class, args);
	}

	@PostConstruct
    public void checkEnv() {
        System.out.println(">> DATABASE_URL: " + System.getenv("DATABASE_URL"));
    }

}
