package com.example.photographer.service.dto.schedule.part.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdminSchedulePartFilter {

    Long eventId;

    Long photographerId;
}
