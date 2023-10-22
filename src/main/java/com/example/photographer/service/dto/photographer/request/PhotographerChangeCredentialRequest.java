package com.example.photographer.service.dto.photographer.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class PhotographerChangeCredentialRequest {

    String email;

    String password;
}
