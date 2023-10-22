package com.example.photographer.service.dto.photographer.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class AdminPhotographerCreateRequest {

    @NotBlank
    String email;

    @NotBlank
    String firstname;

    @NotBlank
    String surname;

    @NotBlank
    String middleName;

    @NotNull
    Boolean trainee;

    String description;
}
