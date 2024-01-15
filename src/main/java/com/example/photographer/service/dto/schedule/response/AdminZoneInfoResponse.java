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

    Long eventId;

    Long photographerId;

    Long photographerScheduleId;

    String firstname;

    String surname;

    String middleName;

    Integer priority;
}
