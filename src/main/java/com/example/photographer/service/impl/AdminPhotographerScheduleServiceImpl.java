package com.example.photographer.service.impl;

import com.example.photographer.config.properties.PhotographerProperties;
import com.example.photographer.domain.*;
import com.example.photographer.exception.NotFoundException;
import com.example.photographer.exception.ScheduleAlreadyExists;
import com.example.photographer.repository.*;
import com.example.photographer.repository.specification.ScheduleSpec;
import com.example.photographer.service.AdminPhotographerScheduleService;
import com.example.photographer.service.dto.AdminListResponse;
import com.example.photographer.service.dto.schedule.request.AdminPhotographerScheduleFilter;
import com.example.photographer.service.dto.schedule.request.AdminPhotographerScheduleRequest;
import com.example.photographer.service.dto.schedule.response.AdminPhotographerScheduleResponse;
import com.example.photographer.service.mapper.PhotographerScheduleMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AdminPhotographerScheduleServiceImpl implements AdminPhotographerScheduleService {

    PhotographerScheduleRepository photographerScheduleRepository;
    EventRepository eventRepository;
    PhotographerRepository photographerRepository;
    ZoneRepository zoneRepository;
    PhotographerEvaluationRepository evaluationRepository;
    PhotographerProperties photographerProperties;

    @Autowired
    public PhotographerScheduleMapper photographerScheduleMapper;

    @Override
    @Transactional(readOnly = true)
    public AdminListResponse<AdminPhotographerScheduleResponse> list(AdminPhotographerScheduleFilter filter, Pageable pageable) {
        Page<PhotographerSchedule> schedules = photographerScheduleRepository.findAllWithFilter(filter.getEventId(), filter.getPhotographerId(), pageable);
        return AdminListResponse.of(schedules.map(photographerScheduleMapper::domainToAdminResponse));
    }

    @Override
    @Transactional(readOnly = true)
    public AdminPhotographerScheduleResponse find(Long id) {
        PhotographerSchedule schedule = photographerScheduleRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        return photographerScheduleMapper.domainToAdminResponse(schedule);
    }

    @Override
    @Transactional
    public void create(AdminPhotographerScheduleRequest request) {
        if (photographerScheduleRepository.existsByPhotographerAndEvent(request.getPhotographerId(), request.getEventId())) {
            throw new ScheduleAlreadyExists();
        }

        Photographer photographer = photographerRepository.findPhotographerById(request.getPhotographerId());
        Event event = eventRepository.findById(request.getEventId()).orElseThrow(() -> new NotFoundException(request.getEventId()));
        Zone zone = zoneRepository.findById(request.getZoneId()).orElseThrow(() -> new NotFoundException(request.getZoneId()));

        PhotographerSchedule schedule = PhotographerSchedule.builder()
                .photographer(photographer)
                .event(event)
                .zone(zone)
                .published(request.getPublished())
                .lastUpdateTime(LocalDateTime.now())
                .build();

        schedule = photographerScheduleRepository.save(schedule);

        evaluationRepository.save(new PhotographerEvaluation(schedule, photographerProperties.getDefaultEvaluation()));
    }

    @Override
    @Transactional
    public void update(Long id, Boolean published, Long zoneId) {
        PhotographerSchedule schedule = photographerScheduleRepository.findById(id).orElseThrow(() -> new NotFoundException(id));

        schedule.setPublished(published);

        if (zoneId != null) {
            Zone zone = zoneRepository.findById(zoneId).orElseThrow(() -> new NotFoundException(zoneId));
            schedule.setZone(zone);
        }

        schedule.setLastUpdateTime(LocalDateTime.now());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        photographerScheduleRepository.deleteById(id);
    }
}
