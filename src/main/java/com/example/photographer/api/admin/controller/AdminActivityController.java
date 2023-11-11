package com.example.photographer.api.admin.controller;

import com.example.photographer.service.AdminActivityService;
import com.example.photographer.service.dto.AdminListResponse;
import com.example.photographer.service.dto.activity.request.AdminActivityBatchUpdateRequest;
import com.example.photographer.service.dto.activity.request.AdminActivityFilter;
import com.example.photographer.service.dto.activity.request.AdminActivityRequest;
import com.example.photographer.service.dto.activity.request.AdminActivityShortFilter;
import com.example.photographer.service.dto.activity.response.AdminActivityResponse;
import com.example.photographer.service.dto.activity.response.AdminActivityShortResponse;
import com.example.photographer.support.api.AdminApi;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@AdminApi
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class AdminActivityController {

    AdminActivityService adminActivityService;

    @GetMapping("/activity/short/all")
    List<AdminActivityShortResponse> findActivityShortList(AdminActivityShortFilter filter) {
        return adminActivityService.findActivityShortList(filter);
    }

    @GetMapping("/activity/all")
    AdminListResponse<AdminActivityResponse> findActivityList(AdminActivityFilter filter, Pageable pageable) {
        return adminActivityService.findActivityList(filter, pageable);
    }

    @GetMapping("/activity/{id}")
    AdminActivityResponse find(@PathVariable Long id) {
        return adminActivityService.find(id);
    }

    @PostMapping("/activity")
    void create(AdminActivityRequest request) {
        adminActivityService.create(request);
    }

    @PutMapping("/activity/{id}")
    void update(@PathVariable Long id) {
        adminActivityService.update(id);
    }

    @PutMapping("/activity/batch")
    void updateInBatch(List<AdminActivityBatchUpdateRequest> requests) {
        adminActivityService.updateInBatch(requests);
    }

    @DeleteMapping("/activity/{id}")
    void delete(@PathVariable Long id) {
        adminActivityService.delete(id);
    }
}
