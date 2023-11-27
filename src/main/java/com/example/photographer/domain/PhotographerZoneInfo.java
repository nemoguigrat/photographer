package com.example.photographer.domain;

import com.example.photographer.support.domain.BaseEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.FieldNameConstants;

import javax.persistence.*;

@Entity
@Table
@Getter
@Builder
@NoArgsConstructor
@FieldNameConstants
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PhotographerZoneInfo extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "zone_id")
    Zone zone;

    @ManyToOne
    @JoinColumn(name = "photographer_id")
    Photographer photographer;

    @Column
    @Setter
    Integer priority;
}
