package com.example.photographer.service.impl;

import com.example.photographer.domain.Photographer;
import com.example.photographer.domain.PhotographerSchedule;
import com.example.photographer.domain.PhotographerZoneInfo;
import com.example.photographer.domain.Zone;
import com.example.photographer.exception.NotFoundException;
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
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AdminZoneInfoServiceImpl implements AdminZoneInfoService {

    PhotographerZonePriorityRepository zoneInfoRepository;
    ZoneRepository zoneRepository;
    PhotographerScheduleRepository photographerScheduleRepository;

    @Override
    @Transactional(readOnly = true)
    public AdminListResponse<AdminZoneInfoResponse> findAll(AdminZoneInfoFilter filter, Pageable pageable) {
        Page<PhotographerZoneInfo> zoneInfo = zoneInfoRepository.findAll(ZoneInfoSpec.filter(filter), pageable);
        return AdminListResponse.of(zoneInfo.map(this::buildResponse));
    }

    @Override
    @Transactional(readOnly = true)
    public AdminZoneInfoResponse find(Long id) {
        PhotographerZoneInfo zoneInfo = zoneInfoRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        return buildResponse(zoneInfo);
    }

    @Override
    @Transactional
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
    @Transactional
    public void update(Long id, AdminZoneInfoRequest request) {
        PhotographerZoneInfo photographerZoneInfo =  zoneInfoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
        photographerZoneInfo.setPriority(request.getPriority());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        zoneInfoRepository.deleteById(id);
    }

    AdminZoneInfoResponse buildResponse(PhotographerZoneInfo zoneInfo) {
        var result = AdminZoneInfoResponse.builder()
                .id(zoneInfo.getId())
                .zoneId(NullSafeUtils.safeGetId(zoneInfo.getZone()))
                .priority(zoneInfo.getPriority());

        if (zoneInfo.getPhotographerSchedule() != null) {
            Photographer photographer = zoneInfo.getPhotographerSchedule().getPhotographer();

            result.photographerScheduleId(NullSafeUtils.safeGetId(zoneInfo.getPhotographerSchedule()))
                    .photographerId(NullSafeUtils.safeGetId(photographer))
                    .eventId(NullSafeUtils.safeGetId(zoneInfo.getPhotographerSchedule().getEvent()))
                    .firstname(photographer.getFirstname())
                    .surname(photographer.getSurname())
                    .middleName(photographer.getMiddleName());

        }

        return result.build();
    }
}
