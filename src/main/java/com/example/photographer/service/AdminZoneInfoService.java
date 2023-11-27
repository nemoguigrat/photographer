package com.example.photographer.service;

import com.example.photographer.service.dto.AdminListResponse;
import com.example.photographer.service.dto.schedule.request.AdminZoneInfoFilter;
import com.example.photographer.service.dto.schedule.request.AdminZoneInfoRequest;
import com.example.photographer.service.dto.schedule.response.AdminZoneInfoResponse;
import org.springframework.data.domain.Pageable;

public interface AdminZoneInfoService {

    AdminListResponse<AdminZoneInfoResponse> findAll(AdminZoneInfoFilter filter, Pageable pageable);

    AdminZoneInfoResponse find(Long id);

    void create(AdminZoneInfoRequest request);

    void update(Long id, AdminZoneInfoRequest request);

    void delete(Long id);
}
