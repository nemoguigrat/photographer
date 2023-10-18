package com.example.photographer.service.dto.photographer.request;

import com.example.photographer.support.UserStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.Map;

@Getter
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class AdminPhotographerUpdateRequest {

    Long id;

    String email;

    String firstname;

    String surname;

    String middleName;

    LocalDate birthdate;

    String phone;

    Map<String, String> contacts;

    UserStatus status;

    String description;

    boolean trainee;
}
