package com.example.photographer.service.impl;

import com.example.photographer.service.dto.auth.AuthRequest;
import com.example.photographer.service.dto.auth.RegisterRequest;
import com.example.photographer.service.dto.auth.LoginResponse;
import com.example.photographer.service.dto.auth.RegistrationResponse;
import com.example.photographer.domain.Employee;
import com.example.photographer.repository.EmployeeRepository;
import com.example.photographer.service.AuthService;
import com.example.photographer.support.UserStatus;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AdminAuthService implements AuthService {


    AuthenticationProvider adminAuthProvider;

    EmployeeRepository employeeRepository;

    @Override
    public LoginResponse login(AuthRequest authRequest) {
        Employee employee = employeeRepository.findEmployeeByEmail(authRequest.getEmail());

        UserStatus status = UserStatus.APPROVED;

        if (!employee.getBlocked()) {
            Authentication authentication = adminAuthProvider.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            employee.getId().toString(),
                            authRequest.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            status = UserStatus.BLOCKED;
        }

        return LoginResponse.builder()
                .id(employee.getId())
                .email(employee.getEmail())
                .status(status)
                .build();
    }

    @Override
    public RegistrationResponse register(RegisterRequest registerRequest) {
       throw new NotImplementedException();
    }
}
