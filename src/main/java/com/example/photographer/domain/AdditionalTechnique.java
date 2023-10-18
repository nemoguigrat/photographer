package com.example.photographer.domain;

import com.example.photographer.service.dto.technique.response.AdditionalTechniqueDto;
import com.example.photographer.support.TechniqueType;
import com.example.photographer.support.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@Table
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@NoArgsConstructor
public class AdditionalTechnique extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "technique_info_id")
    TechniqueInfo techniqueInfo;

    @Column
    String description;

    @Enumerated(EnumType.STRING)
    TechniqueType type;

    public AdditionalTechniqueDto buildDto() {
        return AdditionalTechniqueDto.builder()
                .id(this.getId())
                .description(this.getDescription())
                .type(this.getType())
                .build();
    }
}
