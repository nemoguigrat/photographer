package com.example.photographer.api.admin.controller;

import com.example.photographer.service.AdminFreetimeService;
import com.example.photographer.service.dto.AdminListResponse;
import com.example.photographer.service.dto.schedule.request.AdminFreetimeFilter;
import com.example.photographer.service.dto.schedule.request.AdminFreetimeRequest;
import com.example.photographer.service.dto.schedule.response.AdminFreetimeResponse;
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
public class AdminFreetimeController {

    AdminFreetimeService adminFreetimeService;

    @GetMapping("/freetime/all")
    public AdminListResponse<AdminFreetimeResponse> findAll(@ParameterObject AdminFreetimeFilter filter, @ParameterObject Pageable pageable) {
        return adminFreetimeService.findAllFreetime(filter, pageable);
    }

    @GetMapping("/freetime/{id}")
    public AdminFreetimeResponse find(@PathVariable Long id) {
        return adminFreetimeService.findFreetime(id);
    }

    @PostMapping("/freetime")
    public void create(@RequestBody AdminFreetimeRequest request) {
        adminFreetimeService.createFreetime(request);
    }

    @PutMapping("/freetime/{id}")
    public void update(@PathVariable Long id, @RequestBody AdminFreetimeRequest request) {
        adminFreetimeService.updateFreetime(id, request);
    }

    @DeleteMapping("/freetime/{id}")
    public void delete(@PathVariable Long id) {
        adminFreetimeService.deleteFreetime(id);
    }
}
