package com.example.photographer.service.dto.technique.response;

import com.example.photographer.support.TechniqueType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Builder
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdditionalTechniqueDto {

    Long id;

    String description;

    TechniqueType type;
}
