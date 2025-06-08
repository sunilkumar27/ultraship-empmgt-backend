package com.example.employeemanagement.service.impl;

import com.example.employeemanagement.dto.request.AttendanceRequest;
import com.example.employeemanagement.model.entity.Attendance;
import com.example.employeemanagement.model.entity.Employee;
import com.example.employeemanagement.repository.AttendanceRepository;
import com.example.employeemanagement.repository.EmployeeRepository;
import com.example.employeemanagement.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AttendanceServiceImpl implements AttendanceService {
    private final AttendanceRepository attendanceRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    @Transactional
    public Attendance markAttendance(String employeeId, AttendanceRequest input) {
        log.debug("Marking attendance for employee: {} with input: {}", employeeId, input);

        Employee employee = employeeRepository.findById(employeeId)
            .orElseThrow(() -> new RuntimeException("Employee not found"));

        Attendance attendance = new Attendance();
        attendance.setEmployee(employee);
        attendance.setDate(input.getDate());
        attendance.setStatus(input.getStatus());

        return attendanceRepository.save(attendance);
    }
}