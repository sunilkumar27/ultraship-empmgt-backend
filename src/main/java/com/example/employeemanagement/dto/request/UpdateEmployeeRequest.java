package com.example.employeemanagement.dto.request;

import com.example.employeemanagement.model.enums.Role;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEmployeeRequest {
    private String name;

    @Min(value = 18, message = "Employee must be at least 18 years old")
    private Integer age;

    private String className;
    private Set<String> subjectIds;
    private Role role;
}
