package com.example.photographer.api.web.controller;

import com.example.photographer.service.dto.auth.*;
import com.example.photographer.service.AuthService;
import com.example.photographer.support.UmnUserDetails;
import com.example.photographer.support.api.WebApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

import static lombok.AccessLevel.PRIVATE;

@WebApi
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class ApiAuthController {

    AuthService apiAuthService;

    @Operation(summary = "Аутентификация фотографа")
    @PostMapping("/auth/login")
    public LoginResponse login(@Valid @RequestBody AuthRequest authRequest) {
        return apiAuthService.login(authRequest);
    }

    @Operation(summary = "Регистрация фотографа")
    @PostMapping("/auth/register")
    public RegistrationResponse register(@Valid @RequestBody RegisterRequest authRequest) {
        return apiAuthService.register(authRequest);
    }

    @Operation(summary = "Обновление токена для рассылки пушей")
    @PostMapping("/auth/token/update")
    public void updateToken(@Parameter(hidden = true) @AuthenticationPrincipal UmnUserDetails userDetails, @RequestBody TokenRequest request) {
        apiAuthService.updateToken(userDetails, request);
    }

    @Operation(summary = "Выход из системы")
    @GetMapping("/auth/logout")
    public void logout() {} // swagger doc
}
