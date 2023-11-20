package com.example.photographer.repository.specification;

import com.example.photographer.domain.Activity;
import com.example.photographer.service.dto.activity.request.AdminActivityShortFilter;
import com.example.photographer.support.domain.BaseEntity;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ActivitySpec {

    public static Specification<Activity> shortActivitySpec(AdminActivityShortFilter filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            nullSafePredicateById(root, criteriaBuilder, filter.getLocationId(), Activity.Fields.location, predicates);
            nullSafePredicateById(root, criteriaBuilder, filter.getEventId(), Activity.Fields.event, predicates);
            nullSafePredicateById(root, criteriaBuilder, filter.getZoneId(), Activity.Fields.zone, predicates);

            Predicate from = criteriaBuilder.greaterThanOrEqualTo(root.get(Activity.Fields.startTime), filter.getFrom().atStartOfDay());
            Predicate to = criteriaBuilder.lessThanOrEqualTo(root.get(Activity.Fields.endTime), filter.getTo().atStartOfDay());
            predicates.add(from);
            predicates.add(to);
            return criteriaBuilder.and(from, to);
        };
    }

    public static Specification<Activity> conflictsSpec(Long locationId,
                                                        Long eventId,
                                                        Long zoneId,
                                                        LocalDateTime startTime,
                                                        LocalDateTime endTime) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            nullSafePredicateById(root, criteriaBuilder, locationId, Activity.Fields.location, predicates);
            nullSafePredicateById(root, criteriaBuilder, eventId, Activity.Fields.event, predicates);
            nullSafePredicateById(root, criteriaBuilder, zoneId, Activity.Fields.zone, predicates);

            Predicate time = criteriaBuilder.or(
                    criteriaBuilder.between(root.get("startTime"), startTime, endTime),
                    criteriaBuilder.between(root.get("endTime"), startTime, endTime)
            );
            predicates.add(time);

            query.distinct(true);

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<Activity> notSameId(Long id) {
        return (root, query, criteriaBuilder) ->
            criteriaBuilder.notEqual(root.get(BaseEntity.Fields.id), id);
    }

    private static void nullSafePredicateById(Root<Activity> root,
                                 CriteriaBuilder cb,
                                 Long id,
                                 String field,
                                 Collection<Predicate> predicates) {
        if (id != null) {
            Predicate location = cb.equal(root.get(field).get("id"), id);
            predicates.add(location);
        }
    }
}
