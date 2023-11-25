package com.example.photographer.service.dto.schedule.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class FreetimeRequest {

    Long id;

    LocalDateTime startTime;

    LocalDateTime endTime;
}
