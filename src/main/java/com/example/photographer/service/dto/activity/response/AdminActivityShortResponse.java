package com.example.photographer.service.dto.activity.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class AdminActivityShortResponse {

    String name;

    LocalDateTime startTime;

    LocalDateTime endTime;

    String activityCode;
}
