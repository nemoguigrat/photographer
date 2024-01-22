package com.example.photographer.repository.specification;

import com.example.photographer.domain.PhotographerEvaluation;
import com.example.photographer.domain.PhotographerFreetime;
import com.example.photographer.domain.PhotographerSchedule;
import com.example.photographer.domain.Zone;
import com.example.photographer.service.dto.evaluation.request.AdminEvaluationFilter;
import com.example.photographer.service.dto.zone.request.AdminZoneFilter;
import com.example.photographer.support.domain.BaseEntity;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class EvaluationSpec {
    public static Specification<PhotographerEvaluation> filter(AdminEvaluationFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            Path<PhotographerSchedule> schedule = root.get(PhotographerEvaluation.Fields.photographerSchedule);

            if (filter.getEventId() != null) {
                Predicate event = cb.equal(schedule.get(PhotographerSchedule.Fields.event).get("id"), filter.getEventId());
                predicates.add(event);
            }

            if (filter.getPhotographerId() != null) {
                Predicate photographer = cb.equal(schedule.get(PhotographerSchedule.Fields.photographer).get("id"), filter.getPhotographerId());
                predicates.add(photographer);
            }

            if (Long.class != query.getResultType()) {
                Fetch<?, ?> fetch = root.fetch(PhotographerFreetime.Fields.photographerSchedule, JoinType.LEFT);
                fetch.fetch(PhotographerSchedule.Fields.photographer, JoinType.LEFT);
                fetch.fetch(PhotographerSchedule.Fields.event, JoinType.LEFT);
                fetch.fetch(PhotographerSchedule.Fields.zone, JoinType.LEFT);
                query.distinct(true);
            }

            return predicates.isEmpty() ? null : cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<PhotographerEvaluation> findById(Long id) {
        return (root, query, cb) -> {
            Fetch<?, ?> fetch = root.fetch(PhotographerFreetime.Fields.photographerSchedule, JoinType.LEFT);
            fetch.fetch(PhotographerSchedule.Fields.photographer, JoinType.LEFT);
            fetch.fetch(PhotographerSchedule.Fields.event, JoinType.LEFT);
            fetch.fetch(PhotographerSchedule.Fields.zone, JoinType.LEFT);

            return cb.equal(root.get(BaseEntity.Fields.id), id);
        };
    }

}
