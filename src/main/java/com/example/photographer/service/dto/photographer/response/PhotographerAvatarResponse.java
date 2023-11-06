package com.example.photographer.service.dto.photographer.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.core.io.Resource;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class PhotographerAvatarResponse {

    Resource content;

    String contentType;
}
