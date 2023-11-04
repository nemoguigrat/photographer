package com.example.photographer.service.dto.photographer.request;

import com.example.photographer.support.UserStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Map;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class AdminPhotographerUpdateRequest {

    Long id;

    @NotNull
    String email;

    @NotNull
    String firstname;

    @NotNull
    String surname;

    @NotNull
    String middleName;

    @NotNull
    LocalDate birthdate;

    @NotNull
    String phone;

    Map<String, String> contacts;

    Integer score;

    @NotNull
    UserStatus status;

    String description;

    @NotNull
    Boolean trainee;

    String portfolio;
}
