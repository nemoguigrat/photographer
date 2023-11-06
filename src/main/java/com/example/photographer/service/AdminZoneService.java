package com.example.photographer.service;

import com.example.photographer.service.dto.AdminListResponse;
import com.example.photographer.service.dto.zone.request.AdminZoneFilter;
import com.example.photographer.service.dto.zone.request.AdminZoneRequest;
import com.example.photographer.service.dto.zone.response.AdminZoneResponse;
import org.springframework.data.domain.Pageable;

public interface AdminZoneService {

    AdminListResponse<AdminZoneResponse> findAll(AdminZoneFilter filter, Pageable pageable);

    AdminZoneResponse find(Long id);

    void create(AdminZoneRequest request);

    void update(Long id, AdminZoneRequest request);

    void delete(Long id);
}
