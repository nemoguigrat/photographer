package com.example.photographer.service;

import com.example.photographer.service.dto.AdminListResponse;
import com.example.photographer.service.dto.schedule.part.request.AdminSchedulePartFilter;
import com.example.photographer.service.dto.schedule.part.request.AdminSchedulePartRequest;
import com.example.photographer.service.dto.schedule.part.request.AdminSchedulePartUpdateRequest;
import com.example.photographer.service.dto.schedule.part.response.AdminSchedulePartResponse;
import org.springframework.data.domain.Pageable;

public interface AdminSchedulePartsService {

    AdminListResponse<AdminSchedulePartResponse> findAll(AdminSchedulePartFilter filter, Pageable pageable);

    AdminSchedulePartResponse find(Long id);

    void create(AdminSchedulePartRequest request);

    void update(Long id, AdminSchedulePartUpdateRequest request);

    void delete(Long id);
}
