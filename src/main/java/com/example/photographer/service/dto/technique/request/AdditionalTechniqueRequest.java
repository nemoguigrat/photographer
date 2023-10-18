package com.example.photographer.service.dto.technique.request;

import com.example.photographer.support.TechniqueType;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class AdditionalTechniqueRequest {

    Long id;

    String description;

    TechniqueType type;
}
