package com.example.employeemanagement.security;

import com.example.employeemanagement.model.entity.Employee;
import com.example.employeemanagement.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Custom implementation of UserDetailsService for loading user-specific data.
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
	private static final Logger log = LoggerFactory.getLogger(CustomUserDetailsService.class);
	
    private final EmployeeRepository employeeRepository;
    
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee employee = employeeRepository.findByName(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        
        log.debug("Found employee: {}", employee.getName());
        log.debug("Stored password: {}", employee.getPassword());
        
        UserPrincipal principal = UserPrincipal.create(employee);
        
        log.debug("Created UserPrincipal with password: {}", principal.getPassword());


        return principal;
    }
}