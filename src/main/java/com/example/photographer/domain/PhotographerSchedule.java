package com.example.photographer.domain;

import com.example.photographer.support.domain.BaseEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.FieldNameConstants;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PhotographerSchedule extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    Event event;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "photographer_id", referencedColumnName = "id")
    Photographer photographer;

    @OneToMany(mappedBy = "photographerSchedule", fetch = FetchType.LAZY)
    Set<PhotographerSchedulePart> scheduleParts = new HashSet<>();

    @Column
    @Setter
    Boolean published;

    @Column
    @Setter
    LocalDateTime lastUpdateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zone_id")
    @Setter
    Zone zone;

    // Getters and setters

}
