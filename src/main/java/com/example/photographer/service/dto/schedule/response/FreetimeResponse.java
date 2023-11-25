package com.example.photographer.service.dto.schedule.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class FreetimeResponse {

    Long id;

    LocalDateTime startTime;

    LocalDateTime endTime;
}
