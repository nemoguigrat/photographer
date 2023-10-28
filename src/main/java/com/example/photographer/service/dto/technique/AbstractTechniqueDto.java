package com.example.photographer.service.dto.technique;

import com.example.photographer.service.dto.technique.response.*;
import com.example.photographer.support.TechniqueType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import static lombok.AccessLevel.PRIVATE;

@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
@Schema(anyOf = {CameraDto.class, LensDto.class, BatteryDto.class, MemoryDto.class, FlashDto.class})
public abstract class AbstractTechniqueDto {

    Long id;

    ManufacturerDto manufacturer;

    ModelDto model;

    Integer rating;

    TechniqueType type;
}
