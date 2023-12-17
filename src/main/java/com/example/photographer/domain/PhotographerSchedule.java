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
@AllArgsConstructor
@FieldNameConstants
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PhotographerSchedule extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "event_id")
    Event event;

    @ManyToOne
    @JoinColumn(name = "photographer_id", referencedColumnName = "id")
    Photographer photographer;

    @Column
    @Setter
    Boolean published;

    @Column
    LocalDateTime lastUpdateTime;

    // Getters and setters

}
