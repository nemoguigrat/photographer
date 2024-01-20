package com.example.photographer.repository.specification;

import com.example.photographer.domain.PhotographerSchedule;
import com.example.photographer.domain.PhotographerZoneInfo;
import com.example.photographer.domain.Zone;
import com.example.photographer.service.dto.schedule.request.AdminZoneInfoFilter;
import com.example.photographer.service.dto.zone.request.AdminZoneFilter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class ZoneSpec {
    public static Specification<Zone> filter(AdminZoneFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getEventId() != null) {
                Predicate event = cb.equal(root.get(Zone.Fields.event).get("id"), filter.getEventId());
                predicates.add(event);
            }

            if (Long.class != query.getResultType()) {
                root.fetch(Zone.Fields.event, JoinType.LEFT);
                query.distinct(true);
            }

            return predicates.isEmpty() ? null : cb.and(predicates.toArray(new Predicate[0]));
        };
    }

}
