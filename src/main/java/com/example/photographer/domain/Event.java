package com.example.photographer.domain;

import com.example.photographer.service.dto.event.request.AdminEventRequest;
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
import java.util.TimeZone;

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
    TimeZone timeZone;

    @Column
    String address;

    @Column
    String driveLink;

    @Column
    Integer photographersCount;

    public Event(AdminEventRequest request) {
        applyFromRequest(request);
    }

    public void applyFromRequest(AdminEventRequest request) {
        this.address = request.getAddress();
        this.level = request.getLevel();
        this.name = request.getName();
        this.driveLink = request.getDriveLink();
        this.startTime = request.getStartTime();
        this.endTime = request.getEndTime();
        this.timeZone = TimeZone.getTimeZone(request.getTimeZone());
        this.photographersCount = request.getPhotographersCount();
    }
}
