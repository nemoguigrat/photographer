package com.example.photographer.repository.specification;

import com.example.photographer.domain.PhotographerSchedule;
import com.example.photographer.service.dto.schedule.request.AdminPhotographerScheduleFilter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class ScheduleSpec {

    public static Specification<PhotographerSchedule> filter(AdminPhotographerScheduleFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getEventId() != null) {
                Predicate customerType = cb.equal(root.get(PhotographerSchedule.Fields.event).get("id"), filter.getEventId());
                predicates.add(customerType);
            }

            if (filter.getPhotographerId() != null) {
                Predicate customerType = cb.equal(root.get(PhotographerSchedule.Fields.photographer).get("id"), filter.getPhotographerId());
                predicates.add(customerType);
            }

            if (Long.class != query.getResultType()) {
                root.fetch(PhotographerSchedule.Fields.photographer, JoinType.LEFT);
                root.fetch(PhotographerSchedule.Fields.event, JoinType.LEFT);
                root.fetch(PhotographerSchedule.Fields.zone, JoinType.LEFT);
                query.distinct(true);
            }

            return predicates.isEmpty() ? null : cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
