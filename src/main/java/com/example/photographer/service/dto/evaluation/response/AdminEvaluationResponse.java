package com.example.photographer.service.dto.evaluation.response;

import com.example.photographer.domain.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Optional;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class AdminEvaluationResponse {

    Long id;

    Photographer photographer;

    Event event;

    Zone zone;

    Integer quality;

    Integer punctuality;

    Integer judgment;

    String comment;

    public AdminEvaluationResponse(PhotographerEvaluation evaluation) {
        this.quality = evaluation.getQuality();
        this.punctuality =  evaluation.getPunctuality();
        this.judgment =  evaluation.getJudgment();
        this.comment = evaluation.getComment();

        PhotographerSchedule schedule = evaluation.getPhotographerSchedule();

        if (schedule != null) {
            this.photographer = new Photographer(schedule.getPhotographer());
            this.event = new Event(schedule.getEvent());

            if (schedule.getZone() != null) {
                this.zone = new Zone(schedule.getZone());
            }
        }

    }

    @Getter
    @Setter
    public static class Photographer {

        Long id;

        String firstname;

        String surname;

        String middleName;

        public Photographer(com.example.photographer.domain.Photographer photographer) {
            this.id = photographer.getId();
            this.firstname = photographer.getFirstname();
            this.surname = photographer.getSurname();
            this.middleName = photographer.getMiddleName();
        }
    }

    @Getter
    @Setter
    public static class Event {

        Long id;

        String name;

        Integer level;

        public Event(com.example.photographer.domain.Event event) {
            this.id = event.getId();
            this.name = event.getName();
            this.level = event.getLevel();
        }
    }

    @Getter
    @Setter
    public static class Zone {

        Long id;

        Integer number;

        public Zone(com.example.photographer.domain.Zone zone) {
            this.id = zone.getId();
            this.number = zone.getNumber();
        }
    }
}
