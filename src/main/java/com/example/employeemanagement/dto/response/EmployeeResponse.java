package com.example.employeemanagement.dto.response;

import com.example.employeemanagement.model.enums.Role;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * DTO for employee response data.
 * Contains all necessary employee information for client responses.
 */
@Data
@Builder
public class EmployeeResponse {
    private String id;
    private String name;
    private Integer age;
    private String className;
    private Set<SubjectResponse> subjects;
    private Role role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * Nested DTO for subject information in employee responses.
     */
    @Data
    @Builder
    public static class SubjectResponse {
        private String id;
        private String name;
    }
}