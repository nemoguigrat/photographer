package com.example.photographer.service.impl.auth;

import com.example.photographer.service.dto.auth.*;
import com.example.photographer.domain.Employee;
import com.example.photographer.repository.EmployeeRepository;
import com.example.photographer.service.AuthService;
import com.example.photographer.support.UmnUserDetails;
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

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AdminAuthService implements AuthService {


    AuthenticationProvider adminAuthProvider;

    EmployeeRepository employeeRepository;

    @Override
    public LoginResponse login(AuthRequest authRequest) {
        Employee employee = employeeRepository.findEmployeeByEmail(authRequest.getEmail());

       boolean authenticate = false;

        if (!employee.getBlocked()) {
            Authentication authentication = adminAuthProvider.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            employee.getId().toString(),
                            authRequest.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            employee.setLastLoginTime(LocalDateTime.now());
            authenticate = true;
        }

        return LoginResponse.builder()
                .id(employee.getId())
                .email(employee.getEmail())
                .status(authenticate ? UserStatus.CREATED : UserStatus.BLOCKED)
                .authenticate(authenticate)
                .build();
    }

    @Override
    public RegistrationResponse register(RegisterRequest registerRequest) {
       throw new NotImplementedException();
    }

    @Override
    public void updateToken(UmnUserDetails userDetails, TokenRequest request) {
        throw new NotImplementedException();
    }
}
