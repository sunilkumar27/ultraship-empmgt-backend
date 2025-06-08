package com.example.employeemanagement.dto.request;

import com.example.employeemanagement.model.enums.SortDirection;
import lombok.Data;

@Data
public class SortInput {
    private String field;
    private SortDirection direction;
}