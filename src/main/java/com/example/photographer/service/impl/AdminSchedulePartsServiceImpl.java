package com.example.photographer.service.impl;

import com.example.photographer.domain.Activity;
import com.example.photographer.domain.PhotographerSchedule;
import com.example.photographer.domain.PhotographerSchedulePart;
import com.example.photographer.exception.NotFoundException;
import com.example.photographer.repository.*;
import com.example.photographer.repository.specification.SchedulePartSpec;
import com.example.photographer.service.AdminSchedulePartsService;
import com.example.photographer.service.dto.AdminListResponse;
import com.example.photographer.service.dto.schedule.part.request.AdminChangeScheduleRequest;
import com.example.photographer.service.dto.schedule.part.request.AdminSchedulePartFilter;
import com.example.photographer.service.dto.schedule.part.request.AdminSchedulePartRequest;
import com.example.photographer.service.dto.schedule.part.request.AdminSchedulePartUpdateRequest;
import com.example.photographer.service.dto.schedule.part.response.AdminSchedulePartResponse;
import com.example.photographer.support.ShootingType;
import com.example.photographer.util.NullSafeUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AdminSchedulePartsServiceImpl implements AdminSchedulePartsService {

    SchedulePartRepository schedulePartRepository;
    PhotographerScheduleRepository photographerScheduleRepository;
    ActivityRepository activityRepository;

    @Override
    @Transactional(readOnly = true)
    public AdminListResponse<AdminSchedulePartResponse> findAll(AdminSchedulePartFilter filter, Pageable pageable) {
        Page<PhotographerSchedulePart> parts = schedulePartRepository.findAll(SchedulePartSpec.filter(filter), pageable);
        return AdminListResponse.of(parts.map(this::buildResponse));
    }

    @Override
    @Transactional(readOnly = true)
    public AdminSchedulePartResponse find(Long id) {
        PhotographerSchedulePart part = schedulePartRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        return buildResponse(part);
    }

    @Override
    @Transactional
    public void create(AdminSchedulePartRequest request) {
        PhotographerSchedulePart part = new PhotographerSchedulePart();
        Activity activity = activityRepository.findById(request.getActivityId()).orElseThrow(() -> new NotFoundException(request.getActivityId()));
        PhotographerSchedule photographerSchedule = photographerScheduleRepository.findByPhotographerId(request.getPhotographerId(), request.getEventId())
                .orElseThrow(() -> new NotFoundException(request.getPhotographerId()));
        part.setActivity(activity);
        part.setPhotographerSchedule(photographerSchedule);
        part.setStartTime(request.getStartTime());
        part.setEndTime(request.getEndTime());
        part.setLastUpdateTime(LocalDateTime.now());
        schedulePartRepository.save(part);
    }

    @Override
    @Transactional
    public void update(Long id, AdminSchedulePartUpdateRequest request) {
        PhotographerSchedulePart part = schedulePartRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        part.setStartTime(request.getStartTime());
        part.setEndTime(request.getEndTime());
        part.setLastUpdateTime(LocalDateTime.now());
    }

    @Override
    @Transactional
    public List<AdminSchedulePartResponse> createFromActivity(AdminChangeScheduleRequest request) {
        PhotographerSchedule schedule = photographerScheduleRepository.findById(request.getScheduleId()).orElseThrow(() -> new NotFoundException(request.getScheduleId()));
        Activity activity = activityRepository.findById(request.getActivityId()).orElseThrow(() -> new NotFoundException(request.getActivityId()));

        ShootingType shootingType = activity.getShootingType();

        List<PhotographerSchedulePart> result = new ArrayList<>();

        PhotographerSchedulePart.PhotographerSchedulePartBuilder builder = PhotographerSchedulePart.builder()
                .photographerSchedule(schedule)
                .activity(activity)
                .lastUpdateTime(LocalDateTime.now());

        //(>_<) мне это в кошмарах снится
        switch (shootingType) {
            case ALL:
                result.add(
                        builder
                                .startTime(activity.getStartTime())
                                .endTime(activity.getEndTime())
                                .build()
                );
                break;
            case START:
                result.add(
                        builder.startTime(activity.getStartTime())
                                .endTime(activity.getEndTime().plusMinutes(activity.getShootingTime().longValue()))
                                .build()
                );
                break;
            case END:
                result.add(
                        builder.startTime(activity.getEndTime().minusMinutes(activity.getShootingTime().longValue()))
                                .endTime(activity.getEndTime())
                                .build()
                );
                break;
            case ANY:
                result.add(
                        builder.startTime(request.getStartTime())
                                .endTime(request.getStartTime().plusMinutes(activity.getShootingTime()))
                                .build()
                );
                break;
            case START_END:
                result.add(
                        builder.startTime(activity.getStartTime())
                                .endTime(activity.getEndTime().plusMinutes(activity.getShootingTime().longValue()))
                                .build()
                );
                result.add(
                        builder.startTime(activity.getEndTime().minusMinutes(activity.getShootingTime().longValue()))
                                .endTime(activity.getEndTime())
                                .build()
                );
                break;


        }

        schedulePartRepository.saveAll(result);

        return result.stream().map(this::buildResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        schedulePartRepository.deleteById(id);
    }

    private AdminSchedulePartResponse buildResponse(PhotographerSchedulePart schedulePart) {
        var result = AdminSchedulePartResponse.builder()
                .id(schedulePart.getId())
                .photographerScheduleId(NullSafeUtils.safeGetId(schedulePart.getPhotographerSchedule()))
                .photographerId(NullSafeUtils.safeGetId(schedulePart.getPhotographerSchedule().getPhotographer()))
                .activityId(NullSafeUtils.safeGetId(schedulePart.getActivity()))
                .startTime(schedulePart.getStartTime())
                .endTime(schedulePart.getEndTime());

        Activity activity = schedulePart.getActivity();
        if (activity != null) {
            result.activityId(activity.getId())
                    .activityName(activity.getName());
        }

        return result.build();
    }
}
