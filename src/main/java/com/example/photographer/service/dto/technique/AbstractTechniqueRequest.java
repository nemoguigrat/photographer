package com.example.photographer.service.dto.technique;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractTechniqueRequest {

    Long id;

    Long manufacturerId;

    Long modelId;

    Integer rating;
}
