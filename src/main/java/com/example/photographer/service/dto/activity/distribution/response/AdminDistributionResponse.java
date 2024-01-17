package com.example.photographer.service.dto.activity.distribution.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class AdminDistributionResponse {

    String code;

    String message;

    Instant eventTime;

}
