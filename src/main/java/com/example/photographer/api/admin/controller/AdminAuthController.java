package com.example.photographer.api.admin.controller;

import com.example.photographer.service.dto.auth.AuthRequest;
import com.example.photographer.service.dto.auth.LoginResponse;
import com.example.photographer.service.AuthService;
import com.example.photographer.service.dto.auth.PingResponse;
import com.example.photographer.support.api.AdminApi;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static lombok.AccessLevel.PRIVATE;

@AdminApi
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class AdminAuthController {

    AuthService adminAuthService;

    @Operation(summary = "Аутентификация в админке")
    @PostMapping("/auth/login")
    public LoginResponse login(@RequestBody AuthRequest authRequest) {
        return adminAuthService.login(authRequest);
    }

    @Operation(summary = "Проверка жизни сессии", description = "Работает как запрос заглушка, если запрос выполнен в рамках сессии, " +
            "то время жизни будет продлено, " +
            "если сессия протухла, то кинет 404")
    @GetMapping("/ping")
    public PingResponse ping() {
        return adminAuthService.ping(null);
    }

    @GetMapping("/auth/logout")
    public void logout() {} // swagger doc
}
