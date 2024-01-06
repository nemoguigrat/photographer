package com.example.photographer.service.dto.event.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class AdminEventRequest {

    String name;

    Integer level;

    LocalDateTime startTime;

    LocalDateTime endTime;

    @Schema(type = "String", example = "Asia/Yerevan")
    ZoneId timeZone;

    String address;

    String driveLink;

    Boolean published;

    Integer photographersCount;

    String description;
}
