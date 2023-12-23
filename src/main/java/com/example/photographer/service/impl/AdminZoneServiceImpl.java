package com.example.photographer.service.impl;

import com.example.photographer.domain.Zone;
import com.example.photographer.exception.DeletionException;
import com.example.photographer.exception.NotFoundException;
import com.example.photographer.repository.ZoneRepository;
import com.example.photographer.repository.specification.ZoneSpec;
import com.example.photographer.service.AdminZoneService;
import com.example.photographer.service.dto.AdminListResponse;
import com.example.photographer.service.dto.zone.request.AdminZoneFilter;
import com.example.photographer.service.dto.zone.request.AdminZoneRequest;
import com.example.photographer.service.dto.zone.response.AdminZoneResponse;
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
public class AdminZoneServiceImpl implements AdminZoneService {

    ZoneRepository zoneRepository;

    @Override
    @Transactional(readOnly = true)
    public AdminListResponse<AdminZoneResponse> findAll(AdminZoneFilter filter, Pageable pageable) {
        Page<Zone> events = zoneRepository.findAll(ZoneSpec.filter(filter), pageable);
        return AdminListResponse.of(events.map(this::buildResponse));
    }

    @Override
    @Transactional(readOnly = true)
    public AdminZoneResponse find(Long id) {
        Zone zone = zoneRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        return buildResponse(zone);
    }

    @Override
    @Transactional
    public void create(AdminZoneRequest request) {
        zoneRepository.save(new Zone(request));
    }

    @Override
    @Transactional
    public void update(Long id, AdminZoneRequest request) {
        Zone zone = zoneRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        zone.applyFromRequest(request);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (zoneRepository.existsLinkedData(id)) {
            throw new DeletionException("Невозможно удалить зону пока с ней связаны другие записи!");
        }

        zoneRepository.deleteById(id);
    }

    public AdminZoneResponse buildResponse(Zone zone) {
        return AdminZoneResponse.builder()
                .id(zone.getId())
                .number(zone.getNumber())
                .description(zone.getDescription())
                .manager(zone.getManager())
                .eventId(NullSafeUtils.safeGetId(zone.getEvent()))
                .build();
    }
}
