package com.example.photographer.domain;

import com.example.photographer.service.dto.evaluation.request.AdminEvaluationRequest;
import com.example.photographer.support.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.FieldNameConstants;

import javax.persistence.*;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Table
@Getter
@Builder
@NoArgsConstructor
@FieldNameConstants
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class PhotographerEvaluation extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "photographer_schedule_id")
    PhotographerSchedule photographerSchedule;

    @Column
    Integer quality;

    @Column
    Integer punctuality;

    @Column
    Integer judgment;

    @Column
    String comment;

    public PhotographerEvaluation(PhotographerSchedule schedule, Integer defaultValue) {
        this.photographerSchedule = schedule;
        this.quality = defaultValue;
        this.punctuality = defaultValue;
        this.judgment = defaultValue;
    }

    public void update(AdminEvaluationRequest request) {
        this.quality = request.getQuality();
        this.punctuality = request.getPunctuality();
        this.judgment = request.getJudgment();
        this.comment = request.getComment();
    }

}
