package com.example.photographer.service.dto.schedule.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdminFreetimeFilter {

    Long photographerId;

    Long eventId;
}
