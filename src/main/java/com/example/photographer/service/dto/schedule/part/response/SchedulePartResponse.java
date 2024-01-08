package com.example.photographer.service.dto.schedule.part.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class SchedulePartResponse {

    Long id;

    Long activityId;

    LocalDateTime startTime;

    LocalDateTime endTime;
}
