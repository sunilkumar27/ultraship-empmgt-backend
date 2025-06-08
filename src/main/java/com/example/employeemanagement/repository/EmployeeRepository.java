package com.example.employeemanagement.repository;

import com.example.employeemanagement.model.entity.Employee;
import com.example.employeemanagement.model.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for managing Employee entities with support for complex queries using JpaSpecificationExecutor.
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String>, JpaSpecificationExecutor<Employee> {
    Optional<Employee> findByName(String name);
    boolean existsByNameAndIdNot(String name, String id);
    long countByRole(Role role);
}