package com.example.photographer.service.impl;

import com.example.photographer.domain.TechniqueInfo;
import com.example.photographer.exception.UserAlreadyExists;
import com.example.photographer.service.dto.auth.AuthRequest;
import com.example.photographer.service.dto.auth.RegisterRequest;
import com.example.photographer.service.dto.auth.LoginResponse;
import com.example.photographer.service.dto.auth.RegistrationResponse;
import com.example.photographer.domain.Photographer;
import com.example.photographer.domain.User;
import com.example.photographer.repository.PhotographerRepository;
import com.example.photographer.repository.UserRepository;
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

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ApiAuthService implements AuthService {


    AuthenticationProvider apiAuthProvider;

    PasswordEncoder passwordEncoder;

    UserRepository userRepository;

    PhotographerRepository photographerRepository;

    @Override
    public LoginResponse login(AuthRequest authRequest) {
        User user = userRepository.findUserByEmail(authRequest.getEmail());

        // не аутентифицируем пользователя, если его не аппровнули
        if (user.getStatus() != UserStatus.APPROVED) {
            Authentication authentication = apiAuthProvider.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getId().toString(),
                            authRequest.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        return LoginResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .status(user.getStatus())
                .build();
    }

    @Override
    @Transactional
    public RegistrationResponse register(RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new UserAlreadyExists();
        }
        TechniqueInfo techniqueInfo = new TechniqueInfo();
        Photographer photographer = buildPhotographer(registerRequest);
        photographer.setTechniqueInfo(techniqueInfo);

        User user = User.builder()
                .photographer(photographer)
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .blocked(false)
                .status(UserStatus.CREATED)
                .registrationDate(LocalDate.now())
                .build();

        userRepository.save(user);

        return RegistrationResponse.builder().id(user.getId()).build();
    }

    private Photographer buildPhotographer(RegisterRequest registerRequest) {
        return Photographer.builder()
                .email(registerRequest.getEmail())
                .birthdate(registerRequest.getBirthdate())
                .phone(registerRequest.getPhone())
                .firstname(registerRequest.getFirstname())
                .surname(registerRequest.getSurname())
                .middleName(registerRequest.getMiddleName())
                .contacts(registerRequest.getContacts())
                .build();
    }
}