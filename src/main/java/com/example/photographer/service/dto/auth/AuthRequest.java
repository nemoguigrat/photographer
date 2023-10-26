package com.example.photographer.service.dto.auth;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthRequest {

    @NotBlank
    String email;

    @NotBlank
    String password;
}
