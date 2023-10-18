package com.example.photographer.service.dto.photographer.response;

import com.example.photographer.support.UserStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdminPhotographerShortResponse {

    Long id;

    String email;

    String firstname;

    String surname;

    String middleName;

    LocalDate birthdate;

    UserStatus status;

    Integer score;
}
