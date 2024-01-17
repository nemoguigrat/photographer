package com.example.photographer.service.dto.activity.distribution.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdminDistributionRequest {

    Long zoneId;

    Long eventId;

    List<Long> photographers;
}
