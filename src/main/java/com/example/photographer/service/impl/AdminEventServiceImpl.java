package com.example.photographer.service.impl;

import com.example.photographer.domain.Event;
import com.example.photographer.event.EventPublishEvent;
import com.example.photographer.exception.NotFoundException;
import com.example.photographer.repository.ActivityRepository;
import com.example.photographer.repository.EventRepository;
import com.example.photographer.service.AdminEventService;
import com.example.photographer.service.dto.AdminListResponse;
import com.example.photographer.service.dto.event.request.AdminEventFilter;
import com.example.photographer.service.dto.event.request.AdminEventRequest;
import com.example.photographer.service.dto.event.response.AdminEventResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AdminEventServiceImpl implements AdminEventService {

    EventRepository eventRepository;
    ActivityRepository activityRepository;
    ApplicationEventPublisher publisher;

    @Override
    @Transactional(readOnly = true)
    public AdminListResponse<AdminEventResponse> findAll(AdminEventFilter filter, Pageable pageable) {
        Page<Event> events = eventRepository.findEventWithFilter(pageable);
        return AdminListResponse.of(events.map(this::buildResponse));
    }

    @Override
    @Transactional(readOnly = true)
    public AdminEventResponse find(Long id) {
        Event event = eventRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        return buildResponse(event);
    }

    @Override
    @Transactional
    public void create(AdminEventRequest request) {
        Event event = eventRepository.save(new Event(request));

        if (!BooleanUtils.toBoolean(request.getPublished()) && BooleanUtils.toBoolean(event.getPublished())) {
            publisher.publishEvent(EventPublishEvent.from(event));
        }
    }

    @Override
    @Transactional
    public void update(Long id, AdminEventRequest request) {
        Event event = eventRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        event.applyFromRequest(request);

        if (!BooleanUtils.toBoolean(request.getPublished()) && BooleanUtils.toBoolean(event.getPublished())) {
            publisher.publishEvent(EventPublishEvent.from(event));
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        eventRepository.deleteById(id);
    }

    /**
     * Поиск недавно измененых событий
     *
     * @param delaySeconds - время, прошедшее с последнего изменения события
     * @return список идентификаторов измененых событий
     */
    @Override
    public List<Long> findLastChangedEvents(long delaySeconds) {
        LocalDateTime delay = LocalDateTime.now().minus(delaySeconds, ChronoUnit.SECONDS);
        return activityRepository.findLastModified(delay);
    }

    public AdminEventResponse buildResponse(Event event) {
        return AdminEventResponse.builder()
                .id(event.getId())
                .address(event.getAddress())
                .level(event.getLevel())
                .name(event.getName())
                .description(event.getDescription())
                .driveLink(event.getDriveLink())
                .startTime(event.getStartTime())
                .endTime(event.getEndTime())
                .timeZone(event.getTimeZone() != null ? event.getTimeZone().toZoneId() : null)
                .photographersCount(event.getPhotographersCount())
                .build();
    }
}
