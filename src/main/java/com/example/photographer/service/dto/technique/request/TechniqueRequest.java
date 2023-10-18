package com.example.photographer.service.dto.technique.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class TechniqueRequest {

    Long id;

    List<CameraRequest> cameras;

    List<LensRequest> lenses;

    List<FlashRequest> flashes;

    List<MemoryRequest> memories;

    List<BatteryRequest> batteries;

    List<AdditionalTechniqueRequest> additionalTechnique;

    boolean laptop;

    String description;

    Integer batteryCount;
}
