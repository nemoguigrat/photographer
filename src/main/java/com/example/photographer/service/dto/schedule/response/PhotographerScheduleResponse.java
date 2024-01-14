package com.example.photographer.service.dto.schedule.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class PhotographerScheduleResponse {

    Long id;

    Long eventId;

    Long zoneId;

    String eventName;

    String eventDescription;

    Boolean published;
}
