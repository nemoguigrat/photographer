package com.example.photographer.service.dto.technique.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TechniqueFilter {

    Long techniqueInfoId;
}
