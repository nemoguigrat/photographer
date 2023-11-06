package com.example.photographer.domain;

import com.example.photographer.support.domain.BaseEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@Table
@Getter
@Builder
@NoArgsConstructor
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
    Integer priority;
}
