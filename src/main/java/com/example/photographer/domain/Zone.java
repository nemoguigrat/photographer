package com.example.photographer.domain;

import com.example.photographer.service.dto.zone.request.AdminZoneRequest;
import com.example.photographer.support.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.FieldNameConstants;

import javax.persistence.*;

import java.util.HashSet;
import java.util.Set;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Table
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
@FieldDefaults(level = PRIVATE)
public class Zone extends BaseEntity {

    @Column
    Integer number;

    @Column
    String description;

    @Column
    String manager;

    @ManyToOne
    @JoinColumn(name = "event_id")
    Event event;

    @OneToMany(mappedBy = "zone")
    Set<Location> locations = new HashSet<>();

    @OneToMany(mappedBy = "zone")
    Set<Activity> activities = new HashSet<>();

    @OneToMany(mappedBy = "zone")
    Set<PhotographerZoneInfo> photographerZones = new HashSet<>();

    public Zone(AdminZoneRequest request) {
        applyFromRequest(request);
    }

    public void applyFromRequest(AdminZoneRequest request) {
        this.number = request.getNumber();
        this.description = request.getDescription();
        this.manager = request.getManager();
    }
}
