package com.example.photographer.domain;

import com.example.photographer.service.dto.technique.ManufacturerDto;
import com.example.photographer.support.TechniqueType;
import com.example.photographer.support.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Table
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Manufacturer extends BaseEntity {

    @Column(nullable = false)
    String name;

    @Enumerated(EnumType.STRING)
    TechniqueType type;

    public ManufacturerDto buildDto() {
        return ManufacturerDto.builder()
                .id(this.getId())
                .name(this.getName())
                .type(this.getType())
                .build();
    }
}
