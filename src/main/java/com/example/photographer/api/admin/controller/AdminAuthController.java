package com.example.photographer.api.admin.controller;

import com.example.photographer.service.dto.auth.AuthRequest;
import com.example.photographer.service.dto.auth.LoginResponse;
import com.example.photographer.service.AuthService;
import com.example.photographer.support.api.AdminApi;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

import static lombok.AccessLevel.PRIVATE;

@AdminApi
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class AdminAuthController {

    AuthService adminAuthService;

    @Operation(summary = "Аутентификация в админке")
    @GetMapping(value = "/auth/login")
    public LoginResponse login(@Valid @ParameterObject AuthRequest authRequest) {
        return adminAuthService.login(authRequest);
    }
}
