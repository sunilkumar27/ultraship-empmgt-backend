package com.example.employeemanagement.controller.graphql;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import com.example.employeemanagement.dto.request.AttendanceRequest;
import com.example.employeemanagement.model.entity.Attendance;
import com.example.employeemanagement.service.AttendanceService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AttendanceController {
    private final AttendanceService attendanceService;

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Attendance markAttendance(
            @Argument String employeeId,
            @Argument("input") AttendanceRequest input) {
        log.debug("Marking attendance for employee: {} with input: {}", employeeId, input);
        return attendanceService.markAttendance(employeeId, input);
    }
}