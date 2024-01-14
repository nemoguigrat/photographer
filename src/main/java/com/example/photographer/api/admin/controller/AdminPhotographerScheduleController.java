package com.example.photographer.api.admin.controller;

import com.example.photographer.service.AdminPhotographerScheduleService;
import com.example.photographer.service.dto.AdminListResponse;
import com.example.photographer.service.dto.schedule.request.AdminPhotographerScheduleFilter;
import com.example.photographer.service.dto.schedule.request.AdminPhotographerScheduleRequest;
import com.example.photographer.service.dto.schedule.response.AdminPhotographerScheduleResponse;
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
public class AdminPhotographerScheduleController {

    AdminPhotographerScheduleService adminPhotographerScheduleService;

    @GetMapping("/schedule/list")
    public AdminListResponse<AdminPhotographerScheduleResponse> list(@ParameterObject AdminPhotographerScheduleFilter filter, @ParameterObject Pageable pageable) {
        return adminPhotographerScheduleService.list(filter, pageable);
    }

    @GetMapping("/schedule/{id}")
    public AdminPhotographerScheduleResponse find(@PathVariable Long id) {
        return adminPhotographerScheduleService.find(id);
    }

    @PostMapping("/schedule")
    public void create(@RequestBody AdminPhotographerScheduleRequest request) {
        adminPhotographerScheduleService.create(request);
    }

    @PutMapping("/schedule/{id}")
    public void update(@PathVariable Long id, @RequestParam Boolean published, @RequestParam(required = false) Long zoneId) {
        adminPhotographerScheduleService.update(id, published, zoneId);
    }

    @DeleteMapping("/schedule/{id}")
    public void delete(@PathVariable Long id) {
        adminPhotographerScheduleService.delete(id);
    }
}
