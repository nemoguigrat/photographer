package com.example.photographer.service.dto.technique.request;

import com.example.photographer.service.dto.technique.AbstractTechniqueRequest;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class BatteryRequest extends AbstractTechniqueRequest {
}
