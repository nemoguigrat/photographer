package com.example.photographer.api.admin.controller;

import com.example.photographer.service.AdminZoneInfoService;
import com.example.photographer.service.dto.AdminListResponse;
import com.example.photographer.service.dto.event.request.AdminEventFilter;
import com.example.photographer.service.dto.event.request.AdminEventRequest;
import com.example.photographer.service.dto.event.response.AdminEventResponse;
import com.example.photographer.service.dto.schedule.request.AdminZoneInfoFilter;
import com.example.photographer.service.dto.schedule.request.AdminZoneInfoRequest;
import com.example.photographer.service.dto.schedule.response.AdminZoneInfoResponse;
import com.example.photographer.support.api.AdminApi;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import static lombok.AccessLevel.PRIVATE;

@AdminApi
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class AdminZoneInfoController {

    AdminZoneInfoService adminZoneInfoService;

    @GetMapping("/zone_info/all")
    public AdminListResponse<AdminZoneInfoResponse> findAll(@ParameterObject AdminZoneInfoFilter filter, @ParameterObject Pageable pageable) {
        return adminZoneInfoService.findAll(filter, pageable);
    }

    @GetMapping("/zone_info/{id}")
    public AdminZoneInfoResponse find(@PathVariable Long id) {
        return adminZoneInfoService.find(id);
    }

    @PostMapping("/zone_info")
    public void create(@RequestBody AdminZoneInfoRequest request) {
        adminZoneInfoService.create(request);
    }

    @PutMapping("/zone_info/{id}")
    public void update(@PathVariable Long id, @RequestBody AdminZoneInfoRequest request) {
        adminZoneInfoService.update(id, request);
    }

    @DeleteMapping("/zone_info/{id}")
    public void delete(@PathVariable Long id) {
        adminZoneInfoService.delete(id);
    }
}
