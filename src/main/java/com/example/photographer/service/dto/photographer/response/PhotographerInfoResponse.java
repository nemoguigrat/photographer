package com.example.photographer.service.dto.photographer.response;

import com.example.photographer.support.ContactType;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(ref = "ContactTypes")
    Map<ContactType, String> contacts;

    Integer score;

    String portfolio;
}
