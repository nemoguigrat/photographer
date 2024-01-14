package com.example.photographer.service;

import com.example.photographer.service.dto.AdminListResponse;
import com.example.photographer.service.dto.schedule.request.AdminPhotographerScheduleFilter;
import com.example.photographer.service.dto.schedule.request.AdminPhotographerScheduleRequest;
import com.example.photographer.service.dto.schedule.response.AdminPhotographerScheduleResponse;
import org.springframework.data.domain.Pageable;

public interface AdminPhotographerScheduleService {

    AdminListResponse<AdminPhotographerScheduleResponse> list(AdminPhotographerScheduleFilter filter, Pageable pageable);

    AdminPhotographerScheduleResponse find(Long id);

    void create(AdminPhotographerScheduleRequest request);

    void update(Long id, Boolean published, Long zoneId);

    void delete(Long id);
}
