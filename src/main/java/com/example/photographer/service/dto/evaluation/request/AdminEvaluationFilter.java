package com.example.photographer.service.dto.evaluation.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdminEvaluationFilter {

    Long eventId;

    Long photographerId;
}
