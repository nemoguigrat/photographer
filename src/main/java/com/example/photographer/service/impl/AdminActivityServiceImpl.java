package com.example.photographer.service.impl;

import com.example.photographer.domain.Activity;
import com.example.photographer.domain.Event;
import com.example.photographer.domain.Location;
import com.example.photographer.domain.Zone;
import com.example.photographer.event.ScheduleChangedEvent;
import com.example.photographer.exception.ActivityConflictException;
import com.example.photographer.exception.NotFoundException;
import com.example.photographer.repository.ActivityRepository;
import com.example.photographer.repository.EventRepository;
import com.example.photographer.repository.LocationRepository;
import com.example.photographer.repository.ZoneRepository;
import com.example.photographer.repository.specification.ActivitySpec;
import com.example.photographer.service.AdminActivityService;
import com.example.photographer.service.dto.AdminListResponse;
import com.example.photographer.service.dto.activity.ActivityLagInfo;
import com.example.photographer.service.dto.activity.request.AdminActivityBatchUpdateRequest;
import com.example.photographer.service.dto.activity.request.AdminActivityFilter;
import com.example.photographer.service.dto.activity.request.AdminActivityRequest;
import com.example.photographer.service.dto.activity.request.AdminActivityShortFilter;
import com.example.photographer.service.dto.activity.response.ActivityConflictResponse;
import com.example.photographer.service.dto.activity.response.AdminActivityResponse;
import com.example.photographer.service.dto.activity.response.AdminActivityShortResponse;
import com.example.photographer.util.ActivityUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AdminActivityServiceImpl implements AdminActivityService {

    ActivityRepository activityRepository;
    ZoneRepository zoneRepository;
    LocationRepository locationRepository;
    EventRepository eventRepository;
    ApplicationEventPublisher publisher;

    @Override
    @Transactional(readOnly = true)
    public List<AdminActivityShortResponse> findActivityShortList(AdminActivityShortFilter filter) {
        List<Activity> activities = activityRepository.findAll(ActivitySpec.shortActivitySpec(filter));
        return activities.stream().map(this::buildActivityShortResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public AdminListResponse<AdminActivityResponse> findActivityList(AdminActivityFilter filter, Pageable pageable) {
        Page<Activity> activities = activityRepository.findAll(pageable);
        return AdminListResponse.of(activities.map(this::buildActivityResponse));
    }

    @Override
    @Transactional(readOnly = true)
    public AdminActivityResponse find(Long id) {
        Activity event = activityRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        return buildActivityResponse(event);
    }

    @Override
    @Transactional
    public void create(AdminActivityRequest request) {
        if (conflictExists(null, request)) {
            throw new ActivityConflictException();
        }

        Activity activity = new Activity(request);
        updateActivityRelationByRef(activity, request);
        Activity saved = activityRepository.saveAndFlush(activity);

        publisher.publishEvent(ScheduleChangedEvent.builder().eventId(saved.getEvent().getId()).build());
    }

    @Override
    @Transactional
    public void update(Long id, AdminActivityRequest request) {
        if (conflictExists(id, request)) {
            throw new ActivityConflictException();
        }

        Activity activity = activityRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        activity.applyFromRequest(request);
        updateActivityRelationByRef(activity, request);

        publisher.publishEvent(ScheduleChangedEvent.builder().eventId(activity.getEvent().getId()).build());
    }

    @Override
    @Transactional
    public List<ActivityConflictResponse> updateInBatch(List<AdminActivityBatchUpdateRequest> requests) {
        List<AdminActivityBatchUpdateRequest> conflicts = ActivityUtils.findConflictingRequests(requests);

        if (!conflicts.isEmpty()) {
            List<Activity> activities = activityRepository.findAllById(
                    conflicts.stream().map(AdminActivityBatchUpdateRequest::getId).collect(Collectors.toList())
            );
            return activities.stream().map(this::buildConflictResponse).collect(Collectors.toList());
        }

        List<Activity> dbConflicts = findDatabaseConflicts(requests);

        if (!dbConflicts.isEmpty()) {
            return dbConflicts.stream().map(this::buildConflictResponse).collect(Collectors.toList());
        }

        List<Activity> activities = activityRepository.findAllById(requests.stream().map(r -> r.getId()).collect(Collectors.toList()));
        Set<Long> events = new HashSet<>();

        for (Activity activity : activities) {
            Optional<AdminActivityBatchUpdateRequest> request = requests.stream()
                    .filter(r -> r.getId() == activity.getId()).findFirst();

            if (request.isPresent()) {
                activity.setStartTime(request.get().getStartTime());
                activity.setEndTime(request.get().getEndTime());
                events.add(request.get().getEventId());
            }
        }

        events.forEach(
                e -> publisher.publishEvent(ScheduleChangedEvent.builder().eventId(e).build())
        );

        return Collections.emptyList();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        activityRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteInBatch(List<Long> ids) {
        activityRepository.deleteByList(ids);
    }

    private AdminActivityResponse buildActivityResponse(Activity activity) {
        return AdminActivityResponse.builder()
                .id(activity.getId())
                .activityCode(activity.getActivityCode())
                .name(activity.getName())
                .eventId(activity.getEvent().getId())
                .locationId(activity.getLocation().getId())
                .startTime(activity.getStartTime())
                .endTime(activity.getEndTime())
                .shootingType(activity.getShootingType())
                .zoneId(activity.getZone().getId())
                .description(activity.getDescription())
                .photographersCount(activity.getPhotographersCount())
                .importantPersons(activity.getImportantPersons())
                .build();
    }

    private AdminActivityShortResponse buildActivityShortResponse(Activity activity) {
        return AdminActivityShortResponse.builder()
                .id(activity.getId())
                .eventId(activity.getEvent().getId())
                .locationId(activity.getLocation().getId())
                .zoneId(activity.getZone().getId())
                .activityCode(activity.getActivityCode())
                .startTime(activity.getStartTime())
                .endTime(activity.getEndTime())
                .description(activity.getDescription())
                .name(activity.getName())
                .build();
    }

    private ActivityConflictResponse buildConflictResponse(Activity activity) {
        return ActivityConflictResponse.builder()
                .id(activity.getId())
                .activityCode(activity.getActivityCode())
                .name(activity.getName())
                .startTime(activity.getStartTime())
                .endTime(activity.getEndTime())
                .build();
    }

    private void updateActivityRelationByRef(Activity activity, AdminActivityRequest request) {
        if (request.getZoneId() != null) {
            Zone zone = zoneRepository.findById(request.getEventId()).orElseThrow(() -> new NotFoundException(request.getZoneId()));
            activity.setZone(zone);
        }

        if (request.getLocationId() != null) {
            Location location = locationRepository.findById(request.getLocationId()).orElseThrow(() -> new NotFoundException(request.getLocationId()));
            activity.setLocation(location);
        }

        if (request.getEventId() != null) {
            Event event = eventRepository.findById(request.getEventId()).orElseThrow(() -> new NotFoundException(request.getEventId()));
            activity.setEvent(event);
        }
    }

    private List<Activity> findDatabaseConflicts(List<AdminActivityBatchUpdateRequest> requests) {
        List<Activity> conflicts = new ArrayList<>();

        Map<String, List<AdminActivityBatchUpdateRequest>> groups = ActivityUtils.groupRequests(requests);

        for (Map.Entry<String, List<AdminActivityBatchUpdateRequest>> entry : groups.entrySet()) {
            String[] keys = entry.getKey().split("-");
            Long locationId = Long.parseLong(keys[0]);
            Long eventId = Long.parseLong(keys[1]);
            Long zoneId = Long.parseLong(keys[2]);
            LocalDateTime startTime = entry.getValue().stream().map(t -> t.getStartTime())
                    .min(Comparator.naturalOrder()).get();
            LocalDateTime endTime = entry.getValue().stream().map(t -> t.getEndTime())
                    .max(Comparator.naturalOrder()).get();

            List<Activity> databaseActivities = activityRepository.findAll(ActivitySpec.conflictsSpec(
                    locationId, eventId, zoneId, startTime, endTime));

            List<ActivityLagInfo> lag = databaseActivities.stream().map(ActivityLagInfo::of).collect(Collectors.toList());
            if (ActivityUtils.hasTimeConflict(lag)) {
                conflicts.addAll(databaseActivities);
            }
        }

        return conflicts;
    }

    private boolean conflictExists(Long id, AdminActivityRequest request) {
        Specification<Activity> specification = ActivitySpec.conflictsSpec(
                request.getLocationId(),
                request.getEventId(),
                request.getZoneId(),
                request.getStartTime(),
                request.getEndTime()
        );
        if (id != null) {
            specification = specification.and(ActivitySpec.notSameId(id));
        }

        return activityRepository.exists(specification);
    }
}
