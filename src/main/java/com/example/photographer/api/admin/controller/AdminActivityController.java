package com.example.photographer.api.admin.controller;

import com.example.photographer.service.AdminActivityService;
import com.example.photographer.service.dto.AdminListResponse;
import com.example.photographer.service.dto.EntityIdList;
import com.example.photographer.service.dto.activity.request.AdminActivityBatchUpdateRequest;
import com.example.photographer.service.dto.activity.request.AdminActivityFilter;
import com.example.photographer.service.dto.activity.request.AdminActivityRequest;
import com.example.photographer.service.dto.activity.request.AdminActivityShortFilter;
import com.example.photographer.service.dto.activity.response.ActivityConflictResponse;
import com.example.photographer.service.dto.activity.response.AdminActivityResponse;
import com.example.photographer.service.dto.activity.response.AdminActivityShortResponse;
import com.example.photographer.service.dto.activity.response.AdminFreeActivityResponse;
import com.example.photographer.support.api.AdminApi;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@AdminApi
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class AdminActivityController {

    AdminActivityService adminActivityService;

    @GetMapping("/activity/short/all")
    List<AdminActivityShortResponse> findActivityShortList(@Valid @ParameterObject AdminActivityShortFilter filter) {
        return adminActivityService.findActivityShortList(filter);
    }

    @GetMapping("/activity/all")
    AdminListResponse<AdminActivityResponse> findActivityList(@ParameterObject AdminActivityFilter filter, @ParameterObject Pageable pageable) {
        return adminActivityService.findActivityList(filter, pageable);
    }

    @GetMapping("/activity/{id}")
    AdminActivityResponse find(@PathVariable Long id) {
        return adminActivityService.find(id);
    }

    @GetMapping("/activity/free")
    AdminListResponse<AdminFreeActivityResponse> findFreeActivity(@RequestParam Long scheduleId,
                                                                  @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)LocalDateTime time,
                                                                  @RequestParam Boolean currentZone,
                                                                  @ParameterObject Pageable pageable) {
        return adminActivityService.findFreeActivity(scheduleId, time, currentZone, pageable);
    }

    @PostMapping("/activity")
    void create(@RequestBody AdminActivityRequest request) {
        adminActivityService.create(request);
    }

    @PutMapping("/activity/{id}")
    void update(@PathVariable Long id, @RequestBody AdminActivityRequest request) {
        adminActivityService.update(id, request);
    }

    @PutMapping("/activity/batch")
    List<ActivityConflictResponse> updateInBatch(@RequestBody List<AdminActivityBatchUpdateRequest> requests) {
        return adminActivityService.updateInBatch(requests);
    }

    @DeleteMapping("/activity/batch")
    void deleteInBatch(@RequestBody EntityIdList ids) {
        adminActivityService.deleteInBatch(ids.getIds());
    }

    @DeleteMapping("/activity/{id}")
    void delete(@PathVariable Long id) {
        adminActivityService.delete(id);
    }
}
