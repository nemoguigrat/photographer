package com.example.photographer.service.dto.event.request;

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

    ZoneId timeZone;

    String address;

    String driveLink;

    Integer photographersCount;
}
