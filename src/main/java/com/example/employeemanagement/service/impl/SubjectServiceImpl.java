package com.example.employeemanagement.service.impl;

import com.example.employeemanagement.model.entity.Subject;
import com.example.employeemanagement.repository.SubjectRepository;
import com.example.employeemanagement.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {
    private final SubjectRepository subjectRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<Subject> getSubjects(Pageable pageable) {
        return subjectRepository.findAll(pageable);
    }
}