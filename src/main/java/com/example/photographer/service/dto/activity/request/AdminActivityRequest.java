package com.example.photographer.service.dto.activity.request;

import com.example.photographer.support.ShootingType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class AdminActivityRequest {

    Long locationId;

    Long eventId;

    Long zoneId;

    String name;

    String description;

    LocalDateTime startTime;

    LocalDateTime endTime;

    Integer photographersCount;

    Integer priority;

    Integer shootingTime;

    ShootingType shootingType;

    String importantPersons;

    String activityCode;
}
