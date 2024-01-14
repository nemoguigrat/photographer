package com.example.photographer.service.impl;

import com.example.photographer.domain.PhotographerSchedule;
import com.example.photographer.domain.PhotographerZoneInfo;
import com.example.photographer.domain.Zone;
import com.example.photographer.exception.NotFoundException;
import com.example.photographer.repository.PhotographerRepository;
import com.example.photographer.repository.PhotographerScheduleRepository;
import com.example.photographer.repository.PhotographerZonePriorityRepository;
import com.example.photographer.repository.ZoneRepository;
import com.example.photographer.repository.specification.ZoneInfoSpec;
import com.example.photographer.service.AdminZoneInfoService;
import com.example.photographer.service.dto.AdminListResponse;
import com.example.photographer.service.dto.schedule.request.AdminZoneInfoFilter;
import com.example.photographer.service.dto.schedule.request.AdminZoneInfoRequest;
import com.example.photographer.service.dto.schedule.response.AdminZoneInfoResponse;
import com.example.photographer.util.NullSafeUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AdminZoneInfoServiceImpl implements AdminZoneInfoService {

    PhotographerZonePriorityRepository zoneInfoRepository;
    PhotographerRepository photographerRepository;
    ZoneRepository zoneRepository;
    PhotographerScheduleRepository photographerScheduleRepository;

    @Override
    public AdminListResponse<AdminZoneInfoResponse> findAll(AdminZoneInfoFilter filter, Pageable pageable) {
        Page<PhotographerZoneInfo> zoneInfo = zoneInfoRepository.findAll(ZoneInfoSpec.filter(filter), pageable);
        return AdminListResponse.of(zoneInfo.map(this::buildResponse));
    }

    @Override
    public AdminZoneInfoResponse find(Long id) {
        PhotographerZoneInfo zoneInfo = zoneInfoRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        return buildResponse(zoneInfo);
    }

    @Override
    public void create(AdminZoneInfoRequest request) {
        Zone zone = zoneRepository.findById(request.getZoneId()).orElseThrow(() -> new NotFoundException(request.getZoneId()));
        PhotographerSchedule photographerSchedule = photographerScheduleRepository.findByPhotographerId(request.getPhotographerId(), zone.getEvent().getId())
                .orElseThrow(() -> new NotFoundException(request.getPhotographerId()));

        zoneInfoRepository.save(PhotographerZoneInfo.builder()
                .photographerSchedule(photographerSchedule)
                .zone(zone)
                .priority(request.getPriority())
                .build());
    }

    @Override
    public void update(Long id, AdminZoneInfoRequest request) {
        PhotographerZoneInfo photographerZoneInfo =  zoneInfoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
        photographerZoneInfo.setPriority(request.getPriority());
    }

    @Override
    public void delete(Long id) {
        zoneInfoRepository.deleteById(id);
    }

    AdminZoneInfoResponse buildResponse(PhotographerZoneInfo zoneInfo) {
        return AdminZoneInfoResponse.builder()
                .id(zoneInfo.getId())
                .photographerScheduleId(NullSafeUtils.safeGetId(zoneInfo.getPhotographerSchedule()))
                .zoneId(NullSafeUtils.safeGetId(zoneInfo.getZone()))
                .priority(zoneInfo.getPriority())
                .build();
    }
}
