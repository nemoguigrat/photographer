package com.example.photographer.service.dto.photographer.request;

import com.example.photographer.support.ContactType;
import io.swagger.v3.oas.annotations.media.Schema;
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
public class PhotographerUpdateRequest {

    String firstname;

    String surname;

    String middleName;

    LocalDate birthdate;

    String phone;

    @Schema(ref = "ContactTypes")
    Map<ContactType, String> contacts;

    String portfolio;
}
