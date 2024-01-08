package com.example.photographer.domain;

import com.example.photographer.service.dto.activity.request.AdminActivityRequest;
import com.example.photographer.support.ShootingType;
import com.example.photographer.support.domain.BaseEntity;
import com.example.photographer.support.domain.DataType;
import com.example.photographer.support.domain.ShootingTime;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Table
@Getter
@Builder
@FieldNameConstants
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class Activity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    @Setter
    Location location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    @Setter
    Event event;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zone_id")
    @Setter
    Zone zone;

    @Column
    String name;

    @Column
    String description;

    @Column
    @Setter
    LocalDateTime startTime;

    @Column
    @Setter
    LocalDateTime endTime;

    @Column
    Integer photographersCount;

    @Column
    Integer priority;

    Integer shootingTime;

    @Enumerated(EnumType.STRING)
    ShootingType shootingType;

    @Column
    String importantPersons;

    @Column
    LocalDateTime lastUpdateTime;

    @Column
    String activityCode;

    public Activity(AdminActivityRequest request) {
        applyFromRequest(request);
    }

    public void applyFromRequest(AdminActivityRequest request) {
        this.name = request.getName();
        this.description = request.getDescription();
        this.startTime = request.getStartTime();
        this.endTime = request.getEndTime();
        this.photographersCount = request.getPhotographersCount();
        this.priority = request.getPriority();
        this.shootingTime = request.getShootingTime();
        this.shootingType = request.getShootingType();
        this.importantPersons = request.getImportantPersons();
        this.activityCode = request.getActivityCode();
        this.lastUpdateTime = LocalDateTime.now();
    }
}
