package com.example.employeemanagement.controller.graphql;

import com.example.employeemanagement.model.entity.Subject;
import com.example.employeemanagement.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class SubjectController {
    private final SubjectService subjectService;

    @QueryMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public Page<Subject> subjects(
            @Argument Integer page,
            @Argument Integer size) {
        return subjectService.getSubjects(
            PageRequest.of(page != null ? page : 0, size != null ? size : 10)
        );
    }
}
