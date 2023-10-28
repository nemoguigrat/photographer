package com.example.photographer.domain;

import com.example.photographer.support.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Table
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class Event extends BaseEntity {

    @Column
    String name;

    @Column
    Integer level;

    @Column
    LocalDateTime startTime;

    @Column
    LocalDateTime endTime;

    @Column
    String timeZone;

    @Column
    String address;

    @Column
    String driveLink;

    @Column
    Integer photographersCount;
}
