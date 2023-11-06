package com.example.photographer.domain;

import com.example.photographer.service.dto.location.request.AdminLocationRequest;
import com.example.photographer.support.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Table
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class Location extends BaseEntity {

    @Column
    String name;

    @Column
    String description;

    @Column
    LocalDate startDate;

    @Column
    LocalDateTime startTime;

    @Column
    LocalDateTime endTime;

    @Column
    String address;

    @Column
    String manager;

    @ManyToOne
    @JoinColumn(name = "zone_id")
    Zone zone;

    @ManyToOne
    @JoinColumn(name = "event_id")
    Event event;

    public Location(AdminLocationRequest request) {
        applyFromRequest(request);
    }

    public void applyFromRequest(AdminLocationRequest request) {
        this.address = request.getAddress();
        this.name = request.getName();
        this.description = request.getDescription();
        this.startTime = request.getStartTime();
        this.endTime = request.getEndTime();
        this.manager = request.getManager();
        this.startDate = request.getStartDate();
    }
}
