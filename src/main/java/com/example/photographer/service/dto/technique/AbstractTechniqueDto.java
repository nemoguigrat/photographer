package com.example.photographer.service.dto.technique;

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
public abstract class AbstractTechniqueDto {

    Long id;

    ManufacturerDto manufacturer;

    ModelDto model;

    Integer rating;
}
