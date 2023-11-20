package com.example.photographer.service.dto.activity.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;


@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class ActivityConflictResponse {

    Long id;

    String name;

    String activityCode;

    LocalDateTime startTime;

    LocalDateTime endTime;

}
