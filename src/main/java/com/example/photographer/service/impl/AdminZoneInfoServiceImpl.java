package com.example.photographer.service.impl;

import com.example.photographer.domain.Photographer;
import com.example.photographer.domain.PhotographerFreetime;
import com.example.photographer.domain.PhotographerZoneInfo;
import com.example.photographer.domain.Zone;
import com.example.photographer.exception.NotFoundException;
import com.example.photographer.repository.PhotographerRepository;
import com.example.photographer.repository.PhotographerZonePriorityRepository;
import com.example.photographer.repository.ZoneRepository;
import com.example.photographer.repository.specification.FreetimeSpec;
import com.example.photographer.repository.specification.ZoneInfoSpec;
import com.example.photographer.service.AdminZoneInfoService;
import com.example.photographer.service.dto.AdminListResponse;
import com.example.photographer.service.dto.schedule.request.AdminZoneInfoFilter;
import com.example.photographer.service.dto.schedule.request.AdminZoneInfoRequest;
import com.example.photographer.service.dto.schedule.response.AdminZoneInfoResponse;
import com.example.photographer.service.dto.schedule.response.PriorityResponse;
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

    @Override
    public AdminListResponse<AdminZoneInfoResponse> findAll(AdminZoneInfoFilter filter, Pageable pageable) {
        Page<PhotographerZoneInfo> freetimes = zoneInfoRepository.findAll(ZoneInfoSpec.filter(filter), pageable);
        return AdminListResponse.of(freetimes.map(this::buildResponse));
    }

    @Override
    public AdminZoneInfoResponse find(Long id) {
        PhotographerZoneInfo freetime = zoneInfoRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        return buildResponse(freetime);
    }

    @Override
    public void create(AdminZoneInfoRequest request) {
        Photographer photographerRef = photographerRepository.getReferenceById(request.getPhotographerId());
        Zone zoneRef = zoneRepository.getReferenceById(request.getZoneId());

        zoneInfoRepository.save(PhotographerZoneInfo.builder()
                .photographer(photographerRef)
                .zone(zoneRef)
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
                .photographerId(NullSafeUtils.safeGetId(zoneInfo.getPhotographer()))
                .zoneId(NullSafeUtils.safeGetId(zoneInfo.getZone()))
                .priority(zoneInfo.getPriority())
                .build();
    }
}
