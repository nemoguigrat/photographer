package com.example.photographer.service.dto.photographer.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Map;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PhotographerInfoResponse {

    Long id;

    String firstname;

    String surname;

    String middleName;

    LocalDate birthdate;

    String email;

    String phone;

    Map<String, String> contacts;

    Integer score;
}
