package com.example.photographer.api.admin.controller;

import com.example.photographer.service.AdminSchedulePartsService;
import com.example.photographer.service.dto.AdminListResponse;
import com.example.photographer.service.dto.schedule.part.request.AdminChangeScheduleRequest;
import com.example.photographer.service.dto.schedule.part.request.AdminSchedulePartFilter;
import com.example.photographer.service.dto.schedule.part.request.AdminSchedulePartRequest;
import com.example.photographer.service.dto.schedule.part.request.AdminSchedulePartUpdateRequest;
import com.example.photographer.service.dto.schedule.part.response.AdminSchedulePartResponse;
import com.example.photographer.support.api.AdminApi;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@AdminApi
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class AdminSchedulePartsController {

    AdminSchedulePartsService adminSchedulePartsService;

    @GetMapping("/schedule_part/all")
    AdminListResponse<AdminSchedulePartResponse> findAll(@ParameterObject AdminSchedulePartFilter filter, @ParameterObject Pageable pageable) {
        return adminSchedulePartsService.findAll(filter, pageable);
    }

    @GetMapping("/schedule_part/{id}")
    AdminSchedulePartResponse find(@PathVariable Long id) {
        return adminSchedulePartsService.find(id);
    }

    @PostMapping("/schedule_part")
    void create(@RequestBody AdminSchedulePartRequest request) {
        adminSchedulePartsService.create(request);
    }

    @PutMapping("/schedule_part/{id}")
    void update(@PathVariable Long id, @RequestBody AdminSchedulePartUpdateRequest request) {
        adminSchedulePartsService.update(id, request);
    }

    @PostMapping("/schedule_part/create_from_activity")
    List<AdminSchedulePartResponse> createFromActivity(@RequestBody AdminChangeScheduleRequest request) {
        return adminSchedulePartsService.createFromActivity(request);
    }

    @DeleteMapping("/schedule_part/{id}")
    void delete(@PathVariable Long id) {
        adminSchedulePartsService.delete(id);
    }
}
