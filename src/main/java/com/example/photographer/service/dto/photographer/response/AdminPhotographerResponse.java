package com.example.photographer.service.dto.photographer.response;

import com.example.photographer.support.UserStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Map;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdminPhotographerResponse {

    Long id;

    String email;

    String firstname;

    String surname;

    String middleName;

    LocalDate birthdate;

    String phone;

    Map<String, String> contacts;

    Integer score;

    UserStatus status;

    LocalDate registrationDate;

    String description;

    boolean trainee;
}
