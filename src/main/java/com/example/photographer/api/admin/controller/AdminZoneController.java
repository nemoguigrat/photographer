package com.example.photographer.api.admin.controller;

import com.example.photographer.service.AdminZoneService;
import com.example.photographer.service.dto.AdminListResponse;
import com.example.photographer.service.dto.zone.request.AdminZoneFilter;
import com.example.photographer.service.dto.zone.request.AdminZoneRequest;
import com.example.photographer.service.dto.zone.response.AdminZoneResponse;
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
public class AdminZoneController {

    AdminZoneService adminZoneService;

    @GetMapping("/zone/all")
    AdminListResponse<AdminZoneResponse> findAll(@ParameterObject AdminZoneFilter filter, @ParameterObject Pageable pageable) {
        return adminZoneService.findAll(filter, pageable);
    }

    @GetMapping("/zone/{id}")
    AdminZoneResponse find(@PathVariable Long id) {
        return adminZoneService.find(id);
    }

    @PostMapping("/zone")
    void create(@RequestBody AdminZoneRequest request) {
        adminZoneService.create(request);
    }

    @PutMapping("/zone/{id}")
    void update(@PathVariable Long id, @RequestBody AdminZoneRequest request) {
        adminZoneService.update(id, request);
    }

    @DeleteMapping("/zone/{id}")
    void delete(@PathVariable Long id) {
        adminZoneService.delete(id);
    }
}
