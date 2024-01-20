package com.example.photographer.service.dto.activity.response;

import com.example.photographer.support.FreeActivityStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class AdminFreeActivityResponse {

    Long id;

    String name;

    Long locationId;

    Integer priority;

    FreeActivityStatus status;

    Long zoneId;

    Integer zoneNumber;
}
