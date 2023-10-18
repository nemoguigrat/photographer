package com.example.photographer.service.dto.auth;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthRequest {

    String email;

    String password;
}
