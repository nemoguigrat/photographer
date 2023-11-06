package com.example.photographer.service;

import com.example.photographer.service.dto.AdminListResponse;
import com.example.photographer.service.dto.location.request.AdminLocationFilter;
import com.example.photographer.service.dto.location.request.AdminLocationRequest;
import com.example.photographer.service.dto.location.response.AdminLocationResponse;
import org.springframework.data.domain.Pageable;

public interface AdminLocationService {

    AdminListResponse<AdminLocationResponse> findAll(AdminLocationFilter filter, Pageable pageable);

    AdminLocationResponse find(Long id);

    void create(AdminLocationRequest request);

    void update(Long id, AdminLocationRequest request);

    void delete(Long id);
}
