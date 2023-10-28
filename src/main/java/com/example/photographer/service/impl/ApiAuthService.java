package com.example.photographer.service.impl;

import com.example.photographer.domain.TechniqueInfo;
import com.example.photographer.exception.UserAlreadyExists;
import com.example.photographer.service.dto.auth.AuthRequest;
import com.example.photographer.service.dto.auth.RegisterRequest;
import com.example.photographer.service.dto.auth.LoginResponse;
import com.example.photographer.service.dto.auth.RegistrationResponse;
import com.example.photographer.domain.Photographer;
import com.example.photographer.repository.PhotographerRepository;
import com.example.photographer.service.AuthService;
import com.example.photographer.support.UserStatus;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ApiAuthService implements AuthService {


    AuthenticationProvider apiAuthProvider;

    PasswordEncoder passwordEncoder;
    
    PhotographerRepository photographerRepository;

    @Override
    @Transactional
    public LoginResponse login(AuthRequest authRequest) {
        Photographer photographer = photographerRepository.findPhotographerByEmail(authRequest.getEmail());
        boolean authenticate = false;
        // не аутентифицируем пользователя, если его не аппровнули
        if (photographer.getStatus() == UserStatus.APPROVED) {
            Authentication authentication = apiAuthProvider.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            photographer.getId().toString(),
                            authRequest.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            photographer.setLastLoginTime(LocalDateTime.now());
            authenticate = true;
        }

        return LoginResponse.builder()
                .id(photographer.getId())
                .email(photographer.getEmail())
                .status(photographer.getStatus())
                .authenticate(authenticate)
                .build();
    }

    @Override
    @Transactional
    public RegistrationResponse register(RegisterRequest registerRequest) {
        if (photographerRepository.existsByEmail(registerRequest.getEmail())) {
            throw new UserAlreadyExists();
        }
        TechniqueInfo techniqueInfo = new TechniqueInfo();
        Photographer photographer = buildPhotographer(registerRequest);
        photographer.setTechniqueInfo(techniqueInfo);

        photographerRepository.save(photographer);

        return RegistrationResponse.builder().id(photographer.getId()).build();
    }

    private Photographer buildPhotographer(RegisterRequest registerRequest) {
        return Photographer.builder()
                .birthdate(registerRequest.getBirthdate())
                .phone(registerRequest.getPhone())
                .firstname(registerRequest.getFirstname())
                .surname(registerRequest.getSurname())
                .middleName(registerRequest.getMiddleName())
                .contacts(registerRequest.getContacts())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .status(UserStatus.CREATED)
                .registrationDate(LocalDate.now())
                .build();
    }
}
