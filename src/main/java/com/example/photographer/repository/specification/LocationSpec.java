package com.example.photographer.repository.specification;

import com.example.photographer.domain.Location;
import com.example.photographer.service.dto.location.request.AdminLocationFilter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class LocationSpec {

    public static Specification<Location> filter(AdminLocationFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getZoneId() != null) {
                Predicate zone = cb.equal(root.get(Location.Fields.zone).get("id"), filter.getZoneId());
                predicates.add(zone);
            }

            if (filter.getEventId() != null) {
                Predicate event = cb.equal(root.get(Location.Fields.event).get("id"), filter.getEventId());
                predicates.add(event);
            }

            if (Long.class != query.getResultType()) {
                root.fetch(Location.Fields.event, JoinType.LEFT);
                root.fetch(Location.Fields.zone, JoinType.LEFT);
                query.distinct(true);
            }

            return predicates.isEmpty() ? null : cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
