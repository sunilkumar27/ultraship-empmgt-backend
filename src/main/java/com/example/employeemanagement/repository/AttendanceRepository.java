package com.example.employeemanagement.repository;

import com.example.employeemanagement.model.entity.Attendance;
import com.example.employeemanagement.model.enums.AttendanceStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository for managing Attendance entities.
 * Provides methods for attendance-related database operations.
 */
@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, String>, 
        JpaSpecificationExecutor<Attendance> {

    /**
     * Finds attendance records for a specific employee.
     *
     * @param employeeId The ID of the employee
     * @param pageable Pagination information
     * @return Page of attendance records
     */
    Page<Attendance> findByEmployeeId(String employeeId, Pageable pageable);

    /**
     * Finds attendance records for a specific date range.
     *
     * @param startDate Start of the date range
     * @param endDate End of the date range
     * @param employeeId The ID of the employee
     * @return List of attendance records
     */
    List<Attendance> findByDateBetweenAndEmployeeId(
            LocalDate startDate, 
            LocalDate endDate, 
            String employeeId);

    /**
     * Calculates attendance statistics for an employee.
     *
     * @param employeeId The ID of the employee
     * @param startDate Start date for statistics
     * @param endDate End date for statistics
     * @return Count of attendance by status
     */
    @Query("SELECT a.status as status, COUNT(a) as count FROM Attendance a " +
           "WHERE a.employee.id = :employeeId " +
           "AND a.date BETWEEN :startDate AND :endDate " +
           "GROUP BY a.status")
    List<AttendanceStatusCount> getAttendanceStats(
            @Param("employeeId") String employeeId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    /**
     * Interface for attendance statistics results.
     */
    interface AttendanceStatusCount {
        AttendanceStatus getStatus();
        Long getCount();
    }
}