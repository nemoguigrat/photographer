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
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PhotographerSchedulePart extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "activity_id")
    Activity activity;

    @ManyToOne
    @JoinColumn(name = "photographer_schedule_id")
    PhotographerSchedule photographerSchedule;

    @Column
    LocalDateTime startTime;

    @Column
    LocalDateTime endTime;

    @Column
    @Setter
    LocalDateTime lastUpdateTime;

    @Column
    Boolean conflict;

    @Column
    LocalDateTime notifiedTime;
}