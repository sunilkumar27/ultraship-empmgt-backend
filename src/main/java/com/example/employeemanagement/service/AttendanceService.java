package com.example.employeemanagement.service;

import com.example.employeemanagement.dto.request.AttendanceRequest;
import com.example.employeemanagement.model.entity.Attendance;

public interface AttendanceService {
    Attendance markAttendance(String employeeId, AttendanceRequest input);
}