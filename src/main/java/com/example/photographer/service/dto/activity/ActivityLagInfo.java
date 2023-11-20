package com.example.photographer.service.dto.activity;

import com.example.photographer.domain.Activity;
import com.example.photographer.service.dto.activity.request.AdminActivityBatchUpdateRequest;
import com.example.photographer.util.NullSafeUtils;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value(staticConstructor = "of")
@Builder
public class ActivityLagInfo {

    Long id;

    Long locationId;

    Long eventId;

    Long zoneId;

    LocalDateTime startTime;

    LocalDateTime endTime;

    public static ActivityLagInfo of(AdminActivityBatchUpdateRequest request) {
        return ActivityLagInfo.builder()
                .id(request.getId())
                .locationId(request.getLocationId())
                .eventId(request.getEventId())
                .zoneId(request.getZoneId())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .build();
    }

    public static ActivityLagInfo of(Activity activity) {
        return ActivityLagInfo.builder()
                .id(activity.getId())
                .locationId(NullSafeUtils.safeGetId(activity.getLocation()))
                .eventId(NullSafeUtils.safeGetId(activity.getEvent()))
                .zoneId(NullSafeUtils.safeGetId(activity.getZone()))
                .startTime(activity.getStartTime())
                .endTime(activity.getEndTime())
                .build();
    }
}
