package com.example.photographer.service.dto.zone.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdminZoneFilter {

    Long eventId;
}
