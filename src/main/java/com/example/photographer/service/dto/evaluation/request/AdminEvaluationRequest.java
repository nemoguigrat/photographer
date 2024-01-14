package com.example.photographer.service.dto.evaluation.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class AdminEvaluationRequest {

    Integer quality;

    Integer punctuality;

    Integer judgment;

    String comment;
}
