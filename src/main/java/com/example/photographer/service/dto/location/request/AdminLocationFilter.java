package com.example.photographer.service.dto.location.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdminLocationFilter {

    Long eventId;

    Long zoneId;
}
