package com.example.photographer.service.dto.auth;

import com.example.photographer.support.ContactType;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(ref = "ContactTypes")
    Map<ContactType, String> contacts;

    @NotBlank
    String email;

    @NotBlank
    String password;
}
