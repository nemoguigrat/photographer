package com.example.photographer.domain;

import com.example.photographer.support.domain.BaseEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PhotographerSchedulePart extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "activity_id")
    Activity activity;

    @ManyToOne
    @JoinColumn(name = "photographer_schedule_id")
    PhotographerSchedule photographerScheduleId;

    @Column
    LocalDateTime startTime;

    @Column
    LocalDateTime endTime;

    @Column
    LocalDateTime lastUpdateTime;

    @Column
    Boolean conflict;
}