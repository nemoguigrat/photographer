package com.example.photographer.service;

import com.example.photographer.service.dto.auth.*;
import com.example.photographer.support.UmnUserDetails;

public interface AuthService {

    LoginResponse login(AuthRequest authRequest);

    RegistrationResponse register(RegisterRequest registerRequest);

    void updateToken(UmnUserDetails userDetails, TokenRequest request);

    PingResponse ping(Long userId);
}
