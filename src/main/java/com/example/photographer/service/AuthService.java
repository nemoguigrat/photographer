package com.example.photographer.service;

import com.example.photographer.service.dto.auth.AuthRequest;
import com.example.photographer.service.dto.auth.RegisterRequest;
import com.example.photographer.service.dto.auth.LoginResponse;
import com.example.photographer.service.dto.auth.RegistrationResponse;

public interface AuthService {

    LoginResponse login(AuthRequest authRequest);

    RegistrationResponse register(RegisterRequest registerRequest);
}
