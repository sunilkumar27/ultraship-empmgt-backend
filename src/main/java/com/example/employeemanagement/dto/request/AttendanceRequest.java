package com.example.employeemanagement.dto.request;

import java.time.LocalDate;

import com.example.employeemanagement.model.enums.AttendanceStatus;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Input DTO for marking attendance.
 * Contains the date and status of the attendance record.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceRequest {
    @NotNull(message = "Date is required")
    private LocalDate date;

    @NotNull(message = "Status is required")
    private AttendanceStatus status;
}
