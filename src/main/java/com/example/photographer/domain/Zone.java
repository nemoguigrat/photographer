package com.example.photographer.domain;

import com.example.photographer.support.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Table
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class Zone extends BaseEntity {

    @Column
    String name;

    @Column
    String description;

    @Column
    String manager;

    @ManyToOne
    @JoinColumn(name = "event_id")
    Event event;
}
