package com.example.photographer.service;

import com.example.photographer.service.dto.AdminListResponse;
import com.example.photographer.service.dto.event.request.AdminEventFilter;
import com.example.photographer.service.dto.event.request.AdminEventRequest;
import com.example.photographer.service.dto.event.response.AdminEventResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

public interface AdminEventService {

    AdminListResponse<AdminEventResponse> findAll(AdminEventFilter filter, Pageable pageable);

    AdminEventResponse find(Long id);

    void create(AdminEventRequest request);

    void update(@PathVariable Long id, @RequestBody AdminEventRequest request);

    void delete(@PathVariable Long id);
}
