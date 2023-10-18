package com.example.photographer.domain;

import com.example.photographer.service.dto.technique.ModelDto;
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
public class Model extends BaseEntity {

    @Column(nullable = false)
    String name;

    @Enumerated(EnumType.STRING)
    TechniqueType type;

    public ModelDto buildDto() {
        return ModelDto.builder()
                .id(this.getId())
                .name(this.getName())
                .type(this.getType())
                .build();
    }
}
