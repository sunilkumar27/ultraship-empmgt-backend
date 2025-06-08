package com.example.employeemanagement.security;

import com.example.employeemanagement.model.entity.Employee;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserPrincipal implements UserDetails {
    private String id;
    private String name;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public static UserPrincipal create(Employee employee) {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + employee.getRole().name());
        return new UserPrincipal(
            employee.getId(),
            employee.getName(),
            employee.getPassword(),
            Collections.singletonList(authority)
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}