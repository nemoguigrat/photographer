package com.example.photographer.api.admin.controller;

import com.example.photographer.service.dto.photographer.request.AdminPhotographerCreateRequest;
import com.example.photographer.service.dto.photographer.request.AdminPhotographerUpdateRequest;
import com.example.photographer.service.dto.photographer.response.AdminPhotographerResponse;
import com.example.photographer.service.dto.photographer.response.AdminPhotographerShortResponse;
import com.example.photographer.support.api.AdminApi;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@AdminApi
@Deprecated
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class AdminPhotographerController {

    @GetMapping("/photographer/all")
    public List<AdminPhotographerShortResponse> findAllPhotographers() {
        return null;
    }

    @GetMapping("/photographer/{id}")
    public AdminPhotographerResponse findPhotographer(@PathVariable Long id) {
        return null;
    }

    @PostMapping("/photographer/create")
    public void createPhotographer(@RequestBody AdminPhotographerCreateRequest request) {

    }

    @PostMapping("/photographer/edit")
    public void updatePhotographer(@RequestBody AdminPhotographerUpdateRequest request) {

    }

    @PostMapping("/photographer/{id}/reset_password")
    public void resetPassword(@PathVariable String id) {

    }
}
