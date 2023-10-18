package com.example.photographer.service.dto.technique.response;

import com.example.photographer.service.dto.technique.AbstractTechniqueDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BatteryDto extends AbstractTechniqueDto {
}
