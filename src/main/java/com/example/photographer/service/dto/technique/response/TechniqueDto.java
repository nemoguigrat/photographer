package com.example.photographer.service.dto.technique.response;

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

    List<CameraDto> cameras;

    List<LensDto> lenses;

    List<FlashDto> flashes;

    List<MemoryDto> memories;

    List<BatteryDto> batteries;

    List<AdditionalTechniqueDto> additionalTechnique;

    boolean laptop;

    String description;

    Integer batteryCount;
}
