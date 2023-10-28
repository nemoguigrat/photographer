package com.example.photographer.domain;

import com.example.photographer.support.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@NoArgsConstructor
public class TechniqueInfo extends BaseEntity {

    @OneToMany(mappedBy = "techniqueInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<Camera> cameras = new HashSet<>();

    @OneToMany(mappedBy = "techniqueInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<Flash> flashes = new HashSet<>();

    @OneToMany(mappedBy = "techniqueInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<Lens> lenses = new HashSet<>();

    @OneToMany(mappedBy = "techniqueInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<Memory> memories = new HashSet<>();

    @OneToMany(mappedBy = "techniqueInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<Battery> batteries = new HashSet<>();

    @OneToMany(mappedBy = "techniqueInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<AdditionalTechnique> additionalTechniques = new HashSet<>();

    @OneToOne(mappedBy = "techniqueInfo")
    Photographer photographer;

    @Column
    @Setter
    boolean laptop;

    @Column
    @Setter
    String description;

    @Column
    @Setter
    Integer batteryCount;
}