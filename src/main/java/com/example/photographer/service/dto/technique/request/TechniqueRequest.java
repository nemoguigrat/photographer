package com.example.photographer.service.dto.technique.request;

import com.example.photographer.service.dto.technique.AbstractTechniqueRequest;
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

    List<AbstractTechniqueRequest> technique;

    List<AdditionalTechniqueRequest> additionalTechnique;

    boolean laptop;

    String description;

    Integer batteryCount;
}
