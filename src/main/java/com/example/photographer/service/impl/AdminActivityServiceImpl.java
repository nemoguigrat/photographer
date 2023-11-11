package com.example.photographer.service.impl;

import com.example.photographer.service.AdminActivityService;
import com.example.photographer.service.dto.AdminListResponse;
import com.example.photographer.service.dto.activity.request.AdminActivityBatchUpdateRequest;
import com.example.photographer.service.dto.activity.request.AdminActivityFilter;
import com.example.photographer.service.dto.activity.request.AdminActivityRequest;
import com.example.photographer.service.dto.activity.request.AdminActivityShortFilter;
import com.example.photographer.service.dto.activity.response.AdminActivityResponse;
import com.example.photographer.service.dto.activity.response.AdminActivityShortResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AdminActivityServiceImpl implements AdminActivityService {

    @Override
    @Transactional(readOnly = true)
    public List<AdminActivityShortResponse> findActivityShortList(AdminActivityShortFilter filter) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public AdminListResponse<AdminActivityResponse> findActivityList(AdminActivityFilter filter, Pageable pageable) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public AdminActivityResponse find(Long id) {
        return null;
    }

    @Override
    @Transactional
    public void create(AdminActivityRequest request) {

    }

    @Override
    @Transactional
    public void update(Long id) {

    }

    @Override
    @Transactional
    public void updateInBatch(List<AdminActivityBatchUpdateRequest> requests) {

    }

    @Override
    @Transactional
    public void delete(Long id) {

    }
}
