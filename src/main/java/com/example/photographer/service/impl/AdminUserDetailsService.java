package com.example.photographer.service.impl;

import com.example.photographer.domain.Employee;
import com.example.photographer.repository.EmployeeRepository;
import com.example.photographer.support.UmnUserDetails;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AdminUserDetailsService implements UserDetailsService {

    EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee employee = employeeRepository.findEmployeeById(Long.parseLong(username));

        if (Boolean.TRUE.equals(employee.getBlocked())) {
            throw new UsernameNotFoundException("msg");
        }

        String id = employee.getId().toString();
        String password = employee.getPassword();
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_ADMIN");
        return new UmnUserDetails(id, password, List.of(grantedAuthority));
    }
}
