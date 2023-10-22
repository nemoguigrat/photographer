package com.example.photographer.api.admin.controller;

import com.example.photographer.service.AdminPhotographerService;
import com.example.photographer.service.dto.photographer.request.AdminPhotographerCreateRequest;
import com.example.photographer.service.dto.photographer.request.AdminPhotographerFilter;
import com.example.photographer.service.dto.photographer.response.AdminPhotographerList;
import com.example.photographer.service.dto.photographer.request.AdminPhotographerUpdateRequest;
import com.example.photographer.service.dto.photographer.response.AdminPhotographerResponse;
import com.example.photographer.support.api.AdminApi;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static lombok.AccessLevel.PRIVATE;

@AdminApi
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class AdminPhotographerController {

    AdminPhotographerService adminPhotographerService;

    @GetMapping("/photographer/all")
    public AdminPhotographerList findAllPhotographers(@ParameterObject AdminPhotographerFilter filter, @ParameterObject Pageable pageable) {
        return adminPhotographerService.findAll(filter, pageable);
    }

    @GetMapping("/photographer/{id}")
    public AdminPhotographerResponse findPhotographer(@PathVariable Long id) {
        return adminPhotographerService.find(id);
    }

    @PostMapping("/photographer/create")
    public void createPhotographer(@Valid @RequestBody AdminPhotographerCreateRequest request) {
        adminPhotographerService.create(request);
    }

    @PutMapping("/photographer/edit")
    public void updatePhotographer(@Valid @RequestBody AdminPhotographerUpdateRequest request) {
        adminPhotographerService.update(request);
    }

    @Deprecated
    @PostMapping("/photographer/{id}/reset_password")
    public void resetPassword(@PathVariable Long id) {
        adminPhotographerService.resetPassword(id);
    }
}
