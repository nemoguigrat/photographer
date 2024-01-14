package com.example.photographer.service.dto.schedule.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class AdminZoneInfoResponse {

    Long id;

    Long zoneId;

    Long photographerScheduleId;

    Integer priority;
}
