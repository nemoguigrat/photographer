package com.example.photographer.domain;

import com.example.photographer.support.ShootingType;
import com.example.photographer.support.domain.BaseEntity;
import com.example.photographer.support.domain.DataType;
import com.example.photographer.support.domain.ShootingTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Table
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class Activity extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "location_id")
    Location location;

    @ManyToOne
    @JoinColumn(name = "event_id")
    Event event;

    @ManyToOne
    @JoinColumn(name = "zone_id")
    Zone zone;

    @Column
    String name;

    @Column
    String description;

    @Column
    LocalDateTime startTime;

    @Column
    LocalDateTime endTime;

    @Column
    Integer photographersCount;

    @Column
    Integer priority;

    @Type(type = DataType.JSONB)
    @Column(columnDefinition = DataType.JSONB)
    List<ShootingTime> shootingTime;

    @Enumerated(EnumType.STRING)
    ShootingType shootingType;

    @Column
    String importantPersons;

    @Column
    LocalDateTime lastUpdateTime;

    @Column
    String activityCode;
}
