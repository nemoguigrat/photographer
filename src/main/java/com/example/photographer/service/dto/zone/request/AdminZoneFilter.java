package com.example.photographer.service.dto.zone.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class AdminZoneFilter {

    Long eventId;
}
