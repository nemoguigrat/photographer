package com.example.photographer.service.dto.schedule.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class AdminPhotographerScheduleResponse {

    Long id;

    Long eventId;

    Long photographerId;

    Boolean published;

    String firstname;

    String surname;

    String middleName;
}
