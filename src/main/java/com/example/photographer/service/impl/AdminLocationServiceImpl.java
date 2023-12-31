package com.example.photographer.service.impl;

import com.example.photographer.domain.Location;
import com.example.photographer.domain.Zone;
import com.example.photographer.exception.NotFoundException;
import com.example.photographer.repository.LocationRepository;
import com.example.photographer.repository.ZoneRepository;
import com.example.photographer.repository.specification.LocationSpec;
import com.example.photographer.service.AdminLocationService;
import com.example.photographer.service.dto.AdminListResponse;
import com.example.photographer.service.dto.location.response.AdminLocationResponse;
import com.example.photographer.service.dto.location.request.AdminLocationFilter;
import com.example.photographer.service.dto.location.request.AdminLocationRequest;
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
public class AdminLocationServiceImpl implements AdminLocationService {

    LocationRepository locationRepository;
    ZoneRepository zoneRepository;

    @Override
    @Transactional(readOnly = true)
    public AdminListResponse<AdminLocationResponse> findAll(AdminLocationFilter filter, Pageable pageable) {
        Page<Location> locations = locationRepository.findAll(LocationSpec.filter(filter), pageable);
        return AdminListResponse.of(locations.map(this::buildResponse));
    }

    @Override
    @Transactional(readOnly = true)
    public AdminLocationResponse find(Long id) {
        Location location = locationRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        return buildResponse(location);
    }

    @Override
    @Transactional
    public void create(AdminLocationRequest request) {
        Location location = new Location(request);
        updateRelation(location, request);
        locationRepository.save(location);
    }

    @Override
    @Transactional
    public void update(Long id, AdminLocationRequest request) {
        Location location = locationRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        location.applyFromRequest(request);
        updateRelation(location, request);
    }

    private void updateRelation(Location location, AdminLocationRequest request) {
        Zone zone = zoneRepository.findAndFetchEvent(request.getZoneId()).orElseThrow(() -> new NotFoundException(request.getZoneId()));
        location.setZone(zone);
        location.setEvent(zone.getEvent());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        locationRepository.deleteById(id);
    }

    private AdminLocationResponse buildResponse(Location location) {
        return AdminLocationResponse.builder()
                .id(location.getId())
                .startDate(location.getStartDate())
                .startTime(location.getStartTime())
                .endTime(location.getEndTime())
                .name(location.getName())
                .description(location.getDescription())
                .address(location.getAddress())
                .manager(location.getManager())
                .eventId(NullSafeUtils.safeGetId(location.getEvent()))
                .zoneId(NullSafeUtils.safeGetId(location.getZone()))
                .build();
    }
}
