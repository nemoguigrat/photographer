package com.example.photographer.service.dto.schedule.part.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class AdminSchedulePartFilter {

    Long eventId;

    Long photographerId;
}
