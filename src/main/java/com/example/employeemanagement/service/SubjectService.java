package com.example.employeemanagement.service;

import com.example.employeemanagement.model.entity.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SubjectService {
    Page<Subject> getSubjects(Pageable pageable);
}
