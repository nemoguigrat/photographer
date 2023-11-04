package com.example.photographer.service;

import com.example.photographer.service.dto.AdminListResponse;
import com.example.photographer.service.dto.photographer.request.AdminPhotographerCreateRequest;
import com.example.photographer.service.dto.photographer.request.AdminPhotographerFilter;
import com.example.photographer.service.dto.photographer.request.AdminPhotographerUpdateRequest;
import com.example.photographer.service.dto.photographer.response.AdminPhotographerResponse;
import org.springframework.data.domain.Pageable;

public interface AdminPhotographerService {

    AdminListResponse<AdminPhotographerResponse> findAll(AdminPhotographerFilter filter, Pageable pageable);

    AdminPhotographerResponse find(Long id);

    void create(AdminPhotographerCreateRequest request);

    void update(AdminPhotographerUpdateRequest request);

    void resetPassword(Long id);
}
