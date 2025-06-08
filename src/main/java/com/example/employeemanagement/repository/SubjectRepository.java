package com.example.employeemanagement.repository;

import com.example.employeemanagement.model.entity.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Repository for managing Subject entities.
 * Extends JpaRepository for basic CRUD operations and JpaSpecificationExecutor for complex queries.
 */
@Repository
public interface SubjectRepository extends JpaRepository<Subject, String>, JpaSpecificationExecutor<Subject> {
    
    /**
     * Find a subject by its name.
     *
     * @param name the name of the subject
     * @return Optional containing the subject if found
     */
    Optional<Subject> findByName(String name);
    
    /**
     * Check if a subject exists with the given name.
     *
     * @param name the name to check
     * @return true if a subject exists with the name
     */
    boolean existsByName(String name);
    
    /**
     * Find all subjects with names containing the given string (case-insensitive).
     *
     * @param name the name fragment to search for
     * @param pageable pagination information
     * @return Page of subjects matching the criteria
     */
    Page<Subject> findByNameContainingIgnoreCase(String name, Pageable pageable);
    
    /**
     * Find subjects by multiple IDs.
     *
     * @param ids set of subject IDs to find
     * @return List of found subjects
     */
    List<Subject> findByIdIn(Set<String> ids);
    
    /**
     * Check if all given IDs exist in the database.
     *
     * @param ids set of subject IDs to check
     * @return true if all IDs exist
     */
    @Query("SELECT COUNT(s.id) = :size FROM Subject s WHERE s.id IN :ids")
    boolean existsAllByIds(@Param("ids") Set<String> ids, @Param("size") long size);
    
    /**
     * Find subjects that are not assigned to any employee.
     *
     * @param pageable pagination information
     * @return Page of unassigned subjects
     */
    @Query("SELECT s FROM Subject s WHERE s.id NOT IN (SELECT DISTINCT ss.id FROM Employee e JOIN e.subjects ss)")
    Page<Subject> findUnassignedSubjects(Pageable pageable);
    
    /**
     * Check if a subject with the given name exists, excluding a specific ID.
     * Useful for update operations to check uniqueness.
     *
     * @param name the name to check
     * @param id the ID to exclude from the check
     * @return true if another subject exists with the same name
     */
    boolean existsByNameAndIdNot(String name, String id);
}