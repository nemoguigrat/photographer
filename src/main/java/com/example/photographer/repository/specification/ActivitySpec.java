package com.example.photographer.repository.specification;

import com.example.photographer.domain.Activity;
import com.example.photographer.service.dto.activity.request.AdminActivityShortFilter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;

public class ActivitySpec {

    public static Specification<Activity> shortActivitySpec(AdminActivityShortFilter filter) {
        return (root, query, criteriaBuilder) -> {
            Predicate from = criteriaBuilder.greaterThanOrEqualTo(root.get(Activity.Fields.startTime), filter.getFrom().atStartOfDay());
            Predicate to = criteriaBuilder.lessThanOrEqualTo(root.get(Activity.Fields.endTime), filter.getTo().atStartOfDay());

            return criteriaBuilder.and(from, to);
        };
    }
}
