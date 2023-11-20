package com.example.photographer.util;

import com.example.photographer.service.dto.activity.ActivityLagInfo;
import com.example.photographer.service.dto.activity.request.AdminActivityBatchUpdateRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ActivityUtils {

    public static List<AdminActivityBatchUpdateRequest> findConflictingRequests(List<AdminActivityBatchUpdateRequest> requests) {

        Map<String, List<AdminActivityBatchUpdateRequest>> map = groupRequests(requests);
        List<AdminActivityBatchUpdateRequest> conflictingRequests = new ArrayList<>();

        // Проверяем пересечение временных интервалов для каждой группы запросов
        for (List<AdminActivityBatchUpdateRequest> group : map.values()) {
            List<ActivityLagInfo> lag = group.stream().map(ActivityLagInfo::of).collect(Collectors.toList());

            if (hasTimeConflict(lag)) {
                conflictingRequests.addAll(group);
            }
        }

        return conflictingRequests;
    }

    public static Map<String, List<AdminActivityBatchUpdateRequest>> groupRequests(List<AdminActivityBatchUpdateRequest> requests) {
        Map<String, List<AdminActivityBatchUpdateRequest>> map = new HashMap<>();

        // Группируем запросы по уникальной комбинации locationId, eventId, zoneId
        for (AdminActivityBatchUpdateRequest request : requests) {
            String key = request.getLocationId() + "-" + request.getEventId() + "-" + request.getZoneId();
            map.computeIfAbsent(key, k -> new ArrayList<>()).add(request);
        }

        return map;
    }

    public static boolean hasTimeConflict(List<ActivityLagInfo> requests) {
        for (int i = 0; i < requests.size(); i++) {
            ActivityLagInfo current = requests.get(i);
            for (int j = i + 1; j < requests.size(); j++) {
                ActivityLagInfo other = requests.get(j);
                if (timeOverlaps(current, other)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean timeOverlaps(ActivityLagInfo request1, ActivityLagInfo request2) {
        LocalDateTime startTime1 = request1.getStartTime();
        LocalDateTime endTime1 = request1.getEndTime();
        LocalDateTime startTime2 = request2.getStartTime();
        LocalDateTime endTime2 = request2.getEndTime();

        return startTime1.isBefore(endTime2) && endTime1.isAfter(startTime2);
    }
}
