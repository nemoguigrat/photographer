package com.example.photographer.service.dto.technique;

import com.example.photographer.support.TechniqueType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ModelDto {

    Long id;

    String name;

    TechniqueType type;
}
