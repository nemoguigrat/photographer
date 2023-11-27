package com.example.photographer.service;

import com.example.photographer.service.dto.AdminListResponse;
import com.example.photographer.service.dto.schedule.request.AdminFreetimeFilter;
import com.example.photographer.service.dto.schedule.request.AdminFreetimeRequest;
import com.example.photographer.service.dto.schedule.request.AdminZoneInfoFilter;
import com.example.photographer.service.dto.schedule.request.AdminZoneInfoRequest;
import com.example.photographer.service.dto.schedule.response.AdminFreetimeResponse;
import com.example.photographer.service.dto.schedule.response.AdminZoneInfoResponse;
import org.springframework.data.domain.Pageable;

public interface AdminFreetimeService {

    AdminListResponse<AdminFreetimeResponse> findAllFreetime(AdminFreetimeFilter filter, Pageable pageable);

    AdminFreetimeResponse findFreetime(Long id);

    void createFreetime(AdminFreetimeRequest request);

    void updateFreetime(Long id, AdminFreetimeRequest request);

    void deleteFreetime(Long id);
}
