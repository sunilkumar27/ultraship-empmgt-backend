package com.example.employeemanagement.convertor;

import com.example.employeemanagement.model.enums.AttendanceStatus;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class AttendanceStatusConverter implements AttributeConverter<AttendanceStatus, String> {
    @Override
    public String convertToDatabaseColumn(AttendanceStatus status) {
        return status != null ? status.name() : null;
    }

    @Override
    public AttendanceStatus convertToEntityAttribute(String dbData) {
        return dbData != null ? AttendanceStatus.valueOf(dbData) : null;
    }
}