package com.example.photographer.service.dto.auth;

import com.example.photographer.support.UserStatus;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@FieldDefaults(level = PRIVATE)
public class LoginResponse {

    Long id;

    String email;

    UserStatus status;
}
