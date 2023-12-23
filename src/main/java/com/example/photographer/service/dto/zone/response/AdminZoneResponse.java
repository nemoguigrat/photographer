package com.example.photographer.service.dto.zone.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdminZoneResponse {

    Long id;

    Integer number;

    String description;

    String manager;

    Long eventId;
}
