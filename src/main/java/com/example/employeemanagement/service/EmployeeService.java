package com.example.employeemanagement.service;

import com.example.employeemanagement.dto.request.CreateEmployeeRequest;
import com.example.employeemanagement.dto.request.EmployeeFilter;
import com.example.employeemanagement.dto.request.UpdateEmployeeRequest;
import com.example.employeemanagement.model.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service interface for employee management operations.
 */
public interface EmployeeService {
    Employee createEmployee(CreateEmployeeRequest request);
    Employee updateEmployee(String id, UpdateEmployeeRequest request);
    void deleteEmployee(String id);
    Employee getEmployeeById(String id);
    Page<Employee> getEmployees(Pageable pageable, EmployeeFilter filter);
}