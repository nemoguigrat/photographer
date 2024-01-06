package com.example.photographer.service.dto.event.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class AdminEventResponse {

    Long id;

    String name;

    Integer level;

    LocalDateTime startTime;

    LocalDateTime endTime;

    @Schema(type = "String", example = "Asia/Yerevan")
    ZoneId timeZone;

    String address;

    String driveLink;

    Integer photographersCount;

    Boolean published;

    String description;
}
