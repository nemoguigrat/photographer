package com.example.photographer.service.dto.schedule.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class AdminZoneInfoFilter {

    Long zoneId;

    Long photographerId;

    Long photographerScheduleId;

    Long eventId;
}
