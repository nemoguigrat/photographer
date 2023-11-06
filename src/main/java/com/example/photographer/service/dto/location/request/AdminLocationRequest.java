package com.example.photographer.service.dto.location.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class AdminLocationRequest {

    String name;

    String description;

    LocalDate startDate;

    LocalDateTime startTime;

    LocalDateTime endTime;

    String address;

    String manager;
}
