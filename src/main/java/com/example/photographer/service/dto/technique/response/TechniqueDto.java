package com.example.photographer.service.dto.technique.response;

import com.example.photographer.service.dto.technique.AbstractTechniqueDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TechniqueDto {

    Long id;

    List<AbstractTechniqueDto> technique;

    List<AdditionalTechniqueDto> additionalTechnique;

    boolean laptop;

    String description;

    Integer batteryCount;
}
