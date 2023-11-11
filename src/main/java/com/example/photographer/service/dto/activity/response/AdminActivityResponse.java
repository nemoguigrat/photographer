package com.example.photographer.service.dto.activity.response;

import com.example.photographer.support.ShootingType;
import com.example.photographer.support.domain.ShootingTime;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class AdminActivityResponse {

    Long locationId;

    Long eventId;

    Long zoneId;

    String name;

    String description;

    LocalDateTime startTime;

    LocalDateTime endTime;

    Integer photographersCount;

    Integer priority;

    List<ShootingTime> shootingTime;

    ShootingType shootingType;

    String importantPersons;

    String activityCode;
}
