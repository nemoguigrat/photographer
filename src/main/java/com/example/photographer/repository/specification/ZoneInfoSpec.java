package com.example.photographer.repository.specification;

import com.example.photographer.domain.PhotographerSchedule;
import com.example.photographer.domain.PhotographerZoneInfo;
import com.example.photographer.service.dto.schedule.request.AdminZoneInfoFilter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class ZoneInfoSpec {

    public static Specification<PhotographerZoneInfo> filter(AdminZoneInfoFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getZoneId() != null) {
                Predicate zone = cb.equal(root.get(PhotographerZoneInfo.Fields.zone).get("id"), filter.getEventId());
                predicates.add(zone);
            }

            if (filter.getPhotographerScheduleId() != null) {
                Predicate photographerSchedule = cb.equal(root.get(PhotographerZoneInfo.Fields.photographerSchedule).get("id"), filter.getPhotographerScheduleId());
                predicates.add(photographerSchedule);
            }

            if (filter.getPhotographerId() != null) {
                Predicate photographer = cb.equal(root.get(PhotographerZoneInfo.Fields.photographerSchedule).get(PhotographerSchedule.Fields.photographer).get("id"), filter.getPhotographerId());
                predicates.add(photographer);
            }

            if (Long.class != query.getResultType()) {
                query.distinct(true);
            }

            return predicates.isEmpty() ? null : cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
