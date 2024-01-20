package com.example.photographer.service.dto.schedule.part.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class AdminChangeScheduleRequest {

    Long scheduleId;

    Long activityId;

    LocalDateTime startTime;
}
