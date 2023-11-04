package com.example.photographer.api.admin.controller;

import com.example.photographer.service.AdminEventService;
import com.example.photographer.service.dto.AdminListResponse;
import com.example.photographer.service.dto.event.request.AdminEventFilter;
import com.example.photographer.service.dto.event.request.AdminEventRequest;
import com.example.photographer.service.dto.event.response.AdminEventResponse;
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
public class AdminEventController {

    AdminEventService adminEventService;

    @GetMapping("/event/all")
    public AdminListResponse<AdminEventResponse> findAll(@ParameterObject AdminEventFilter filter, @ParameterObject Pageable pageable) {
        return adminEventService.findAll(filter, pageable);
    }

    @GetMapping("/event/{id}")
    public AdminEventResponse find(@PathVariable Long id) {
        return adminEventService.find(id);
    }

    @PostMapping("/event")
    public void create(@RequestBody AdminEventRequest request) {
        adminEventService.create(request);
    }

    @PutMapping("/event/{id}")
    public void update(@PathVariable Long id, @RequestBody AdminEventRequest request) {
        adminEventService.update(id, request);
    }

    @DeleteMapping("/event/{id}")
    public void delete(@PathVariable Long id) {
        adminEventService.delete(id);
    }
}
