package com.example.photographer.service.dto.event.response;

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

    ZoneId timeZone;

    String address;

    String driveLink;

    Integer photographersCount;
}
