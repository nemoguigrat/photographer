package com.example.photographer.service.impl;

import com.example.photographer.domain.*;
import com.example.photographer.exception.ScheduleAlreadyExists;
import com.example.photographer.repository.*;
import com.example.photographer.service.EventScheduleService;
import com.example.photographer.service.dto.ListResponse;
import com.example.photographer.service.dto.activity.response.ActivityResponse;
import com.example.photographer.service.dto.event.response.EventResponse;
import com.example.photographer.service.dto.location.response.LocationResponse;
import com.example.photographer.service.dto.zone.response.ZoneResponse;
import com.example.photographer.support.UmnUserDetails;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    @Transactional(readOnly = true)
    public ListResponse<EventResponse> events(UmnUserDetails userDetails, Pageable pageable) {
        Page<Event> events = eventRepository.findEventForPhotographer(pageable);
        return ListResponse.of(events.map(this::buildResponse));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ZoneResponse> zones(UmnUserDetails userDetails, Long eventId) {
        List<Zone> zones = zoneRepository.findByEvent_Id(eventId);

        return zones.stream().map(this::buildResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<LocationResponse> locations(UmnUserDetails userDetails, Long eventId) {
       List<Location> locations = locationRepository.findByEvent_Id(eventId);

        return locations.stream().map(this::buildResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ActivityResponse> activities(UmnUserDetails userDetails, Long eventId) {
        List<Activity> activities = activityRepository.findByEvent_Id(eventId);

        return activities.stream().map(this::buildResponse).collect(Collectors.toList());
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
