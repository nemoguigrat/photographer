package com.example.photographer.service.dto.location.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class AdminLocationFilter {

    Long eventId;

    Long zoneId;
}
