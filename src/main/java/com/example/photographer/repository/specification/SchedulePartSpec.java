package com.example.photographer.repository.specification;

import com.example.photographer.domain.PhotographerFreetime;
import com.example.photographer.domain.PhotographerSchedule;
import com.example.photographer.domain.PhotographerSchedulePart;
import com.example.photographer.service.dto.schedule.part.request.AdminSchedulePartFilter;
import com.example.photographer.service.dto.schedule.request.AdminFreetimeFilter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class SchedulePartSpec {

    public static Specification<PhotographerSchedulePart> filter(AdminSchedulePartFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            Path<PhotographerSchedule> schedulePath = root.get(PhotographerFreetime.Fields.photographerSchedule);

            if (filter.getEventId() != null) {
                Predicate customerType = cb.equal(schedulePath.get(PhotographerSchedule.Fields.event).get("id"), filter.getEventId());
                predicates.add(customerType);
            }

            if (filter.getPhotographerId() != null) {
                Predicate customerType = cb.equal(schedulePath.get(PhotographerSchedule.Fields.photographer).get("id"), filter.getPhotographerId());
                predicates.add(customerType);
            }

            if (Long.class != query.getResultType()) {
                Fetch<?, ?> fetch = root.fetch(PhotographerFreetime.Fields.photographerSchedule, JoinType.LEFT);
                fetch.fetch(PhotographerSchedule.Fields.photographer, JoinType.LEFT);
                query.distinct(true);
            }

            return predicates.isEmpty() ? null : cb.and(predicates.toArray(new Predicate[0]));
        };
    }

}
