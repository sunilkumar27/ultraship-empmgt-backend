package com.example.employeemanagement.controller.graphql;

import com.example.employeemanagement.dto.request.CreateEmployeeRequest;
import com.example.employeemanagement.dto.request.EmployeeFilter;
import com.example.employeemanagement.dto.request.SortInput;
import com.example.employeemanagement.dto.request.UpdateEmployeeRequest;
import com.example.employeemanagement.model.entity.Employee;
import com.example.employeemanagement.service.EmployeeService;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

/**
 * GraphQL controller for employee operations.
 * Handles queries and mutations for employee data.
 */
@Controller
@RequiredArgsConstructor
public class EmployeeController {
	private static final Logger log = LoggerFactory.getLogger(EmployeeController.class);
	
    private final EmployeeService employeeService;

    @QueryMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public Page<Employee> employees(
            @Argument Integer page,
            @Argument Integer size,
            @Argument List<SortInput> sort,
            @Argument EmployeeFilter filter) {
        log.debug("Fetching employees with page: {}, size: {}", page, size);
        
        PageRequest pageRequest = PageRequest.of(
            page != null ? page : 0,
            size != null ? size : 10,
            getSortOrders(sort)
        );

        Page<Employee> employees = employeeService.getEmployees(pageRequest, filter);
        
        // Debug log for first employee if exists
        if (employees.hasContent()) {
            Employee firstEmployee = employees.getContent().get(0);
            log.debug("First employee createdAt: {}, updatedAt: {}", 
                     firstEmployee.getCreatedAt(), 
                     firstEmployee.getUpdatedAt());
        }
        
        return employees;
    }

    @QueryMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public Employee employee(@Argument String id) {
        return employeeService.getEmployeeById(id);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Employee createEmployee(@Argument("input") CreateEmployeeRequest request) {
        log.debug("Creating employee with input: {}", request);

        return employeeService.createEmployee(request);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Employee updateEmployee(
            @Argument("id") String id,
            @Argument("input") UpdateEmployeeRequest request) {
        return employeeService.updateEmployee(id, request);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Boolean deleteEmployee(@Argument("id") String id) {
        employeeService.deleteEmployee(id);
        return true;
    }

    private Sort getSortOrders(List<SortInput> sortInputs) {
        if (sortInputs == null || sortInputs.isEmpty()) {
            return Sort.by(Sort.Direction.DESC, "createdAt");
        }

        List<Sort.Order> orders = new ArrayList<>();
        for (SortInput sortInput : sortInputs) {
            Sort.Direction direction = Sort.Direction.valueOf(sortInput.getDirection().name());
            orders.add(new Sort.Order(direction, sortInput.getField()));
        }
        return Sort.by(orders);
    }
}