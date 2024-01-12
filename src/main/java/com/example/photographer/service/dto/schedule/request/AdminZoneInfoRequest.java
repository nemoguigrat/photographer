package com.example.photographer.service.dto.schedule.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class AdminZoneInfoRequest {

    Long zoneId;

    Long photographerId;

    Long photographerScheduleId;

    Integer priority;

    boolean confirmed = false;
}
