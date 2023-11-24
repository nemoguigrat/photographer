package com.example.photographer.service.dto.technique.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class TechniqueFilter {

    Long techniqueInfoId;
}
