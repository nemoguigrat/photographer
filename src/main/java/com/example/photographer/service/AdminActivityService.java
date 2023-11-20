package com.example.photographer.service;

import com.example.photographer.service.dto.AdminListResponse;
import com.example.photographer.service.dto.activity.request.AdminActivityBatchUpdateRequest;
import com.example.photographer.service.dto.activity.request.AdminActivityFilter;
import com.example.photographer.service.dto.activity.request.AdminActivityRequest;
import com.example.photographer.service.dto.activity.request.AdminActivityShortFilter;
import com.example.photographer.service.dto.activity.response.ActivityConflictResponse;
import com.example.photographer.service.dto.activity.response.AdminActivityResponse;
import com.example.photographer.service.dto.activity.response.AdminActivityShortResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AdminActivityService {

    List<AdminActivityShortResponse> findActivityShortList(AdminActivityShortFilter filter);

    AdminListResponse<AdminActivityResponse> findActivityList(AdminActivityFilter filter, Pageable pageable);

    AdminActivityResponse find(Long id);

    void create(AdminActivityRequest request);

    void update(Long id, AdminActivityRequest request);

    List<ActivityConflictResponse> updateInBatch(List<AdminActivityBatchUpdateRequest> requests);

    void delete(Long id);

    void deleteInBatch(List<Long> ids);
}
