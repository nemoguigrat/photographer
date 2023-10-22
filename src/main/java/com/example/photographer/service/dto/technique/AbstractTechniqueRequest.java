package com.example.photographer.service.dto.technique;

import com.example.photographer.service.dto.technique.request.*;
import com.example.photographer.service.dto.technique.response.*;
import com.example.photographer.support.TechniqueType;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.v3.oas.annotations.media.DiscriminatorMapping;
import io.swagger.v3.oas.annotations.media.Schema;
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
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = CameraRequest.class, name = "camera"),
        @JsonSubTypes.Type(value = BatteryRequest.class, name = "battery"),
        @JsonSubTypes.Type(value = FlashRequest.class, name = "flash"),
        @JsonSubTypes.Type(value = LensRequest.class, name = "lens"),
        @JsonSubTypes.Type(value = MemoryRequest.class, name = "memory")
})
public abstract class AbstractTechniqueRequest {

    Long id;

    Long manufacturerId;

    Long modelId;

    Integer rating;

    TechniqueType type;
}
