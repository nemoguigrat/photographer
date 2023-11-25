package com.example.photographer.service.impl;

import com.example.photographer.domain.*;
import com.example.photographer.exception.NotFoundException;
import com.example.photographer.repository.*;
import com.example.photographer.service.ScheduleService;
import com.example.photographer.service.dto.schedule.request.FreetimeRequest;
import com.example.photographer.service.dto.schedule.request.PriorityRequest;
import com.example.photographer.service.dto.schedule.response.FreetimeResponse;
import com.example.photographer.service.dto.schedule.response.PriorityResponse;
import com.example.photographer.support.UmnUserDetails;
import com.example.photographer.util.NullSafeUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ScheduleServiceImpl implements ScheduleService {

    PhotographerFreetimeRepository freetimeRepository;
    PhotographerZonePriorityRepository zonePriorityRepository;
    PhotographerScheduleRepository photographerScheduleRepository;
    ZoneRepository zoneRepository;
    PhotographerRepository photographerRepository;
    EventRepository eventRepository;

    @Override
    @Transactional(readOnly = true)
    public List<FreetimeResponse> freetime(UmnUserDetails userDetails, Long eventId) {
        List<PhotographerFreetime> photographerFreetimes = freetimeRepository.findByPhotographerIdAndEventId(userDetails.getId(), eventId);

        return photographerFreetimes.stream().map(p -> FreetimeResponse.builder()
                .id(p.getId())
                .startTime(p.getStartTime())
                .endTime(p.getEndTime())
                .build()).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PriorityResponse> zonePriority(UmnUserDetails userDetails, Long eventId) {
        List<PhotographerZoneInfo> photographerZoneInfos = zonePriorityRepository.findByPhotographerIdAndEventId(userDetails.getId(), eventId);

        return photographerZoneInfos.stream().map(p -> PriorityResponse.builder()
                .id(p.getId())
                .zoneId(NullSafeUtils.safeGetId(p.getZone()))
                .priority(p.getPriority()).build()).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public void upsertZonePriority(UmnUserDetails userDetails, Long eventId, PriorityRequest request) {
        if (request.getId() != null) {
            PhotographerZoneInfo photographerZoneInfo =  zonePriorityRepository.findById(request.getId())
                    .orElseThrow(() -> new NotFoundException(request.getId()));
            photographerZoneInfo.setPriority(request.getPriority());
            return;
        }

        Photographer photographerRef = photographerRepository.getReferenceById(userDetails.getId());
        Zone zoneRef = zoneRepository.getReferenceById(request.getZoneId());

        zonePriorityRepository.save(PhotographerZoneInfo.builder()
                .photographer(photographerRef)
                .zone(zoneRef)
                .priority(request.getPriority())
                .build());

    }

    @Override
    @Transactional(readOnly = true)
    public void upsertFreeTime(UmnUserDetails userDetails, Long eventId, FreetimeRequest request) {
        if (request.getId() != null) {
            PhotographerFreetime photographerFreetime = freetimeRepository.findById(request.getId())
                    .orElseThrow(() -> new NotFoundException(request.getId()));

            photographerFreetime.setStartTime(request.getStartTime());
            photographerFreetime.setEndTime(request.getEndTime());
            photographerFreetime.setLastUpdateTime(LocalDateTime.now());
            return;
        }

        PhotographerSchedule photographerSchedule = PhotographerSchedule.builder()
                .photographer(photographerRepository.getReferenceById(userDetails.getId()))
                .event(eventRepository.getReferenceById(eventId))
                .published(true)
                .lastUpdateTime(LocalDateTime.now())
                .build();

        PhotographerFreetime photographerFreetime = PhotographerFreetime.builder()
                .photographerSchedule(photographerSchedule)
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .lastUpdateTime(LocalDateTime.now())
                .build();

        photographerScheduleRepository.save(photographerSchedule);
        freetimeRepository.save(photographerFreetime);
    }

    @Override
    @Transactional(readOnly = true)
    public void deleteZonePriority(UmnUserDetails userDetails, Long eventId, Long zonePriorityId) {
        zonePriorityRepository.deleteById(zonePriorityId);
    }

    @Override
    @Transactional(readOnly = true)
    public void deleteFreeTime(UmnUserDetails userDetails, Long eventId, Long freetimeId) {
        freetimeRepository.deleteById(freetimeId);
    }
}
