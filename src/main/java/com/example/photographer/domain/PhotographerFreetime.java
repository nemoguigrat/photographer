package com.example.photographer.domain;

import com.example.photographer.support.domain.BaseEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.FieldNameConstants;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
@Getter
@Builder
@NoArgsConstructor
@FieldNameConstants
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PhotographerFreetime extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "photographer_schedule_id")
    PhotographerSchedule photographerSchedule;

    @Column
    @Setter
    LocalDateTime startTime;

    @Column
    @Setter
    LocalDateTime endTime;

    @Column
    @Setter
    LocalDateTime lastUpdateTime;
}
