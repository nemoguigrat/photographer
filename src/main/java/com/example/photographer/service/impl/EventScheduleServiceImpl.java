package com.example.photographer.service.impl;

import com.example.photographer.domain.*;
import com.example.photographer.exception.NotFoundException;
import com.example.photographer.exception.ScheduleAlreadyExists;
import com.example.photographer.repository.*;
import com.example.photographer.service.EventScheduleService;
import com.example.photographer.service.dto.ListResponse;
import com.example.photographer.service.dto.activity.response.ActivityResponse;
import com.example.photographer.service.dto.event.response.EventResponse;
import com.example.photographer.service.dto.location.response.LocationResponse;
import com.example.photographer.service.dto.schedule.response.PhotographerScheduleResponse;
import com.example.photographer.service.dto.zone.response.ZoneResponse;
import com.example.photographer.service.mapper.PhotographerScheduleMapper;
import com.example.photographer.support.UmnUserDetails;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.example.photographer.util.NullSafeUtils.safeGetId;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class EventScheduleServiceImpl implements EventScheduleService {

    EventRepository eventRepository;
    ZoneRepository zoneRepository;
    ActivityRepository activityRepository;
    LocationRepository locationRepository;
    PhotographerRepository photographerRepository;
    PhotographerScheduleRepository scheduleRepository;

    @Autowired
    public PhotographerScheduleMapper photographerScheduleMapper;

    @Override
    @Transactional(readOnly = true)
    public ListResponse<EventResponse> events(UmnUserDetails userDetails, Pageable pageable) {
        Page<Event> events = eventRepository.findEventForPhotographer(pageable);
        return ListResponse.of(events.map(this::buildResponse));
    }

    @Override
    @Transactional(readOnly = true)
    public ListResponse<ZoneResponse> zones(UmnUserDetails userDetails, Long eventId, Pageable pageable) {
        Page<Zone> zones = zoneRepository.findByEventId(eventId, pageable);

        return ListResponse.of(zones.map(this::buildResponse));
    }

    @Override
    @Transactional(readOnly = true)
    public ListResponse<LocationResponse> locations(UmnUserDetails userDetails, Long eventId, Pageable pageable) {
       Page<Location> locations = locationRepository.findByEventId(eventId, pageable);

        return ListResponse.of(locations.map(this::buildResponse));
    }

    @Override
    @Transactional(readOnly = true)
    public ListResponse<ActivityResponse> activities(UmnUserDetails userDetails, Long eventId, Pageable pageable) {
        Page<Activity> activities = activityRepository.findByEventId(eventId, pageable);

        return ListResponse.of(activities.map(this::buildResponse));
    }

    @Override
    public ZoneResponse zone(UmnUserDetails userDetails, Long zoneId) {
        Zone zone = zoneRepository.findById(zoneId).orElseThrow(() -> new NotFoundException(zoneId));

        return buildResponse(zone);
    }

    @Override
    public LocationResponse location(UmnUserDetails userDetails, Long locationId) {
        Location location = locationRepository.findById(locationId).orElseThrow(() -> new NotFoundException(locationId));

        return buildResponse(location);
    }

    @Override
    public ActivityResponse activity(UmnUserDetails userDetails, Long activityId) {
        Activity activity = activityRepository.findById(activityId).orElseThrow(() -> new NotFoundException(activityId));

        return buildResponse(activity);
    }

    @Override
    @Transactional(readOnly = true)
    public ListResponse<PhotographerScheduleResponse> photographerEvents(UmnUserDetails userDetails, Pageable pageable) {
        Page<PhotographerSchedule> photographerSchedules = scheduleRepository.findByPhotographerId(userDetails.getId(), pageable);

        return ListResponse.of(photographerSchedules.map(photographerScheduleMapper::domainToApiResponse));
    }

    @Override
    @Transactional
    public void register(UmnUserDetails userDetails, Long eventId) {
        if (scheduleRepository.existsByPhotographerAndEvent(userDetails.getId(), eventId)) {
            throw new ScheduleAlreadyExists();
        }

        scheduleRepository.save(PhotographerSchedule.builder()
                .photographer(photographerRepository.getReferenceById(userDetails.getId()))
                .event(eventRepository.getReferenceById(eventId))
                .published(false)
                .lastUpdateTime(LocalDateTime.now())
                .build());
    }

    @Override
    @Transactional
    public void changePublish(UmnUserDetails userDetails, Long eventId, boolean publish) {
        scheduleRepository.findByPhotographerId(userDetails.getId(), eventId).ifPresent(s -> s.setPublished(publish));
    }

    public EventResponse buildResponse(Event event) {
        return EventResponse.builder()
                .id(event.getId())
                .level(event.getLevel())
                .description(event.getDescription())
                .address(event.getAddress())
                .name(event.getName())
                .startTime(event.getStartTime())
                .endTime(event.getEndTime())
                .timeZone(event.getTimeZone() != null ? event.getTimeZone().toZoneId() : null)
                .build();
    }

    public ZoneResponse buildResponse(Zone zone) {
        return ZoneResponse.builder()
                .id(zone.getId())
                .number(zone.getNumber())
                .description(zone.getDescription())
                .manager(zone.getManager())
                .build();
    }

    private LocationResponse buildResponse(Location location) {
        return LocationResponse.builder()
                .id(location.getId())
                .startDate(location.getStartDate())
                .startTime(location.getStartTime())
                .endTime(location.getEndTime())
                .name(location.getName())
                .description(location.getDescription())
                .address(location.getAddress())
                .manager(location.getManager())
                .build();
    }

    private ActivityResponse buildResponse(Activity activity) {
        return ActivityResponse.builder()
                .id(activity.getId())
                .eventId(safeGetId(activity.getEvent()))
                .locationId(safeGetId(activity.getLocation()))
                .zoneId(safeGetId(activity.getZone()))
                .activityCode(activity.getActivityCode())
                .startTime(activity.getStartTime())
                .endTime(activity.getEndTime())
                .description(activity.getDescription())
                .name(activity.getName())
                .build();
    }
}
