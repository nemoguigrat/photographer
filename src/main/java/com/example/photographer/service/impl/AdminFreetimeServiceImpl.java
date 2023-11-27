package com.example.photographer.service.impl;

import com.example.photographer.domain.Location;
import com.example.photographer.domain.PhotographerFreetime;
import com.example.photographer.domain.PhotographerSchedule;
import com.example.photographer.exception.NotFoundException;
import com.example.photographer.repository.EventRepository;
import com.example.photographer.repository.PhotographerFreetimeRepository;
import com.example.photographer.repository.PhotographerRepository;
import com.example.photographer.repository.PhotographerScheduleRepository;
import com.example.photographer.repository.specification.FreetimeSpec;
import com.example.photographer.service.AdminFreetimeService;
import com.example.photographer.service.dto.AdminListResponse;
import com.example.photographer.service.dto.schedule.request.AdminFreetimeFilter;
import com.example.photographer.service.dto.schedule.request.AdminFreetimeRequest;
import com.example.photographer.service.dto.schedule.response.AdminFreetimeResponse;
import com.example.photographer.util.NullSafeUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AdminFreetimeServiceImpl implements AdminFreetimeService {

    PhotographerFreetimeRepository freetimeRepository;
    PhotographerRepository photographerRepository;
    EventRepository eventRepository;
    PhotographerScheduleRepository scheduleRepository;

    @Override
    @Transactional(readOnly = true)
    public AdminListResponse<AdminFreetimeResponse> findAllFreetime(AdminFreetimeFilter filter, Pageable pageable) {
        Page<PhotographerFreetime> freetimes = freetimeRepository.findAll(FreetimeSpec.filter(filter), pageable);
        return AdminListResponse.of(freetimes.map(this::buildResponse));
    }

    @Override
    @Transactional(readOnly = true)
    public AdminFreetimeResponse findFreetime(Long id) {
        PhotographerFreetime freetime = freetimeRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        return buildResponse(freetime);
    }

    @Override
    @Transactional
    public void createFreetime(AdminFreetimeRequest request) {
        PhotographerSchedule photographerSchedule = PhotographerSchedule.builder()
                .photographer(photographerRepository.getReferenceById(request.getPhotographerId()))
                .event(eventRepository.getReferenceById(request.getEventId()))
                .published(true)
                .lastUpdateTime(LocalDateTime.now())
                .build();

        PhotographerFreetime photographerFreetime = PhotographerFreetime.builder()
                .photographerSchedule(photographerSchedule)
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .lastUpdateTime(LocalDateTime.now())
                .build();

        scheduleRepository.save(photographerSchedule);
        freetimeRepository.save(photographerFreetime);
    }

    @Override
    @Transactional
    public void updateFreetime(Long id, AdminFreetimeRequest request) {
        PhotographerFreetime photographerFreetime = freetimeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));

        photographerFreetime.setStartTime(request.getStartTime());
        photographerFreetime.setEndTime(request.getEndTime());
        photographerFreetime.setLastUpdateTime(LocalDateTime.now());
    }

    @Override
    @Transactional
    public void deleteFreetime(Long id) {
        freetimeRepository.deleteById(id);
    }

    AdminFreetimeResponse buildResponse(PhotographerFreetime freetime) {
        return AdminFreetimeResponse.builder()
                .id(freetime.getId())
                .photographerScheduleId(NullSafeUtils.safeGetId(freetime.getPhotographerSchedule()))
                .photographerId(NullSafeUtils.safeGetId(freetime.getPhotographerSchedule().getPhotographer()))
                .startTime(freetime.getStartTime())
                .endTime(freetime.getEndTime())
                .build();

    }
}
