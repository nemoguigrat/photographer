package com.example.photographer.service.impl;

import com.example.photographer.domain.Activity;
import com.example.photographer.exception.NotFoundException;
import com.example.photographer.repository.ActivityRepository;
import com.example.photographer.repository.specification.ActivitySpec;
import com.example.photographer.service.AdminActivityService;
import com.example.photographer.service.dto.AdminListResponse;
import com.example.photographer.service.dto.activity.request.AdminActivityBatchUpdateRequest;
import com.example.photographer.service.dto.activity.request.AdminActivityFilter;
import com.example.photographer.service.dto.activity.request.AdminActivityRequest;
import com.example.photographer.service.dto.activity.request.AdminActivityShortFilter;
import com.example.photographer.service.dto.activity.response.AdminActivityResponse;
import com.example.photographer.service.dto.activity.response.AdminActivityShortResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AdminActivityServiceImpl implements AdminActivityService {

    ActivityRepository activityRepository;

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
        activityRepository.save(new Activity(request));
    }

    @Override
    @Transactional
    public void update(Long id, AdminActivityRequest request) {
        Activity activity = activityRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        activity.applyFromRequest(request);
    }

    @Override
    @Transactional
    public void updateInBatch(List<AdminActivityBatchUpdateRequest> requests) {

    }

    @Override
    @Transactional
    public void delete(Long id) {
        activityRepository.deleteById(id);
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
                .activityCode(activity.getActivityCode())
                .startTime(activity.getStartTime())
                .endTime(activity.getEndTime())
                .description(activity.getDescription())
                .name(activity.getName())
                .build();
    }
}
