package com.example.employeemanagement.dto.request;

import com.example.employeemanagement.model.enums.Role;
import lombok.Data;

@Data
public class EmployeeFilter {
    private String name;
    private IntRange age;
    private String className;
    private Role role;
}