package com.example.photographer.service.dto.zone.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class AdminZoneRequest {

    Integer number;

    String description;

    String manager;
}
