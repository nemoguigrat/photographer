package com.example.photographer.service.dto.auth;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Map;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterRequest {

    @NotBlank
    String firstname;

    @NotBlank
    String surname;

    @NotBlank
    String middleName;

    @NotNull
    LocalDate birthdate;

    @NotBlank
    String phone;

    Map<String, String> contacts;

    @NotBlank
    String email;

    @NotBlank
    String password;
}
