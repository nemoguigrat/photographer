package com.example.photographer.service.dto.location.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class AdminLocationResponse {

    Long id;

    String name;

    String description;

    LocalDate startDate;

    LocalDateTime startTime;

    LocalDateTime endTime;

    String address;

    String manager;

    Long zoneId;

    Long eventId;
}
