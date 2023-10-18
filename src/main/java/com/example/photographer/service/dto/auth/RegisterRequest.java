package com.example.photographer.service.dto.auth;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Map;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterRequest {

    String firstname;

    String surname;

    String middleName;

    LocalDate birthdate;

    String phone;

    Map<String, String> contacts;

    String email;

    String password;
}
