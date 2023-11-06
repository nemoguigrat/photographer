package com.example.photographer.api.admin.controller;

import com.example.photographer.service.AdminLocationService;
import com.example.photographer.service.dto.AdminListResponse;
import com.example.photographer.service.dto.location.request.AdminLocationFilter;
import com.example.photographer.service.dto.location.request.AdminLocationRequest;
import com.example.photographer.service.dto.location.response.AdminLocationResponse;
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
public class AdminLocationController {

    AdminLocationService adminLocationService;

    @GetMapping("/location/all")
    AdminListResponse<AdminLocationResponse> findAll(@ParameterObject AdminLocationFilter filter, @ParameterObject Pageable pageable) {
        return adminLocationService.findAll(filter, pageable);
    }

    @GetMapping("/location/{id}")
    AdminLocationResponse find(@PathVariable Long id) {
        return adminLocationService.find(id);
    }

    @PostMapping("/location")
    void create(@RequestBody AdminLocationRequest request) {
        adminLocationService.create(request);
    }

    @PutMapping("/location/{id}")
    void update(@PathVariable Long id, @RequestBody AdminLocationRequest request) {
        adminLocationService.update(id, request);
    }

    @DeleteMapping("/location/{id}")
    void delete(@PathVariable Long id) {
        adminLocationService.delete(id);
    }
}
