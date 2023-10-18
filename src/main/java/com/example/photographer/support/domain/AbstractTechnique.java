package com.example.photographer.support.domain;

import com.example.photographer.domain.Manufacturer;
import com.example.photographer.domain.Model;
import com.example.photographer.domain.TechniqueInfo;
import com.example.photographer.service.dto.technique.AbstractTechniqueDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@NoArgsConstructor
public abstract class AbstractTechnique extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "technique_info_id")
    TechniqueInfo techniqueInfo;

    @ManyToOne
    @JoinColumn(name = "manufacturer_id")
    Manufacturer manufacturer;

    @ManyToOne
    @JoinColumn(name = "model_id")
    Model model;

    public abstract AbstractTechniqueDto buildDto();
}
