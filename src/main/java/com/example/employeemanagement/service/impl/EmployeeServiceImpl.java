package com.example.employeemanagement.service.impl;

import com.example.employeemanagement.dto.request.CreateEmployeeRequest;
import com.example.employeemanagement.dto.request.EmployeeFilter;
import com.example.employeemanagement.dto.request.UpdateEmployeeRequest;
import com.example.employeemanagement.exception.ResourceNotFoundException;
import com.example.employeemanagement.model.entity.Employee;
import com.example.employeemanagement.model.entity.Subject;
import com.example.employeemanagement.repository.EmployeeRepository;
import com.example.employeemanagement.repository.SubjectRepository;
import com.example.employeemanagement.service.EmployeeService;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
	private static final Logger log = LoggerFactory.getLogger(EmployeeServiceImpl.class);

	private final EmployeeRepository employeeRepository;
	private final SubjectRepository subjectRepository;

	@Override
	@Transactional
	public Employee createEmployee(CreateEmployeeRequest request) {
		Employee employee = new Employee();
		employee.setName(request.getName());
		employee.setAge(request.getAge());
		employee.setClassName(request.getClassName());
		employee.setRole(request.getRole());

		if (request.getSubjectIds() != null && !request.getSubjectIds().isEmpty()) {
			Set<Subject> subjects = fetchSubjects(request.getSubjectIds());
			employee.setSubjects(subjects);
		}

		return employeeRepository.save(employee);
	}

	@Override
	@Transactional
	public Employee updateEmployee(String id, UpdateEmployeeRequest request) {
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));

		if (request.getName() != null) {
			employee.setName(request.getName());
		}
		if (request.getAge() != null) {
			employee.setAge(request.getAge());
		}
		if (request.getClassName() != null) {
			employee.setClassName(request.getClassName());
		}
		if (request.getRole() != null) {
			employee.setRole(request.getRole());
		}
		if (request.getSubjectIds() != null) {
			Set<Subject> subjects = fetchSubjects(request.getSubjectIds());
			employee.setSubjects(subjects);
		}

		return employeeRepository.save(employee);
	}

	@Override
	@Transactional
	public void deleteEmployee(String id) {
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
		employeeRepository.delete(employee);
	}

	@Override
	@Transactional(readOnly = true)
	public Employee getEmployeeById(String id) {
		return employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Employee> getEmployees(Pageable pageable, EmployeeFilter filter) {
		try {

			log.debug("Getting employees with pageable: {} and filter: {}", pageable, filter);

			if (filter == null) {
				return employeeRepository.findAll(pageable);
			}

			Specification<Employee> spec = (root, query, cb) -> {
				List<Predicate> predicates = new ArrayList<>();

				if (filter.getName() != null) {
					predicates.add(cb.like(cb.lower(root.get("name")), "%" + filter.getName().toLowerCase() + "%"));
				}

				if (filter.getClassName() != null) {
					predicates.add(cb.equal(root.get("className"), filter.getClassName()));
				}

				if (filter.getRole() != null) {
					predicates.add(cb.equal(root.get("role"), filter.getRole()));
				}

				if (filter.getAge() != null) {
					if (filter.getAge().getMin() != null) {
						predicates.add(cb.greaterThanOrEqualTo(root.get("age"), filter.getAge().getMin()));
					}
					if (filter.getAge().getMax() != null) {
						predicates.add(cb.lessThanOrEqualTo(root.get("age"), filter.getAge().getMax()));
					}
				}

				return cb.and(predicates.toArray(new Predicate[0]));
			};

			return employeeRepository.findAll(spec, pageable);
		} catch (Exception e) {
			log.error("Error fetching employees", e);
			throw new RuntimeException("Error fetching employees", e);
		}

	}

	private Set<Subject> fetchSubjects(Set<String> subjectIds) {
		return subjectIds.stream()
				.map(subjectId -> subjectRepository.findById(subjectId)
						.orElseThrow(() -> new ResourceNotFoundException("Subject not found with id: " + subjectId)))
				.collect(Collectors.toCollection(HashSet::new));
	}
}