package com.example.photographer.api.web.controller;

import com.example.photographer.service.dto.photographer.request.PhotographerUpdateRequest;
import com.example.photographer.service.dto.photographer.response.PhotographerInfoResponse;
import com.example.photographer.service.PhotographerService;
import com.example.photographer.support.UmnUserDetails;
import com.example.photographer.support.api.WebApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@WebApi
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class PhotographerController {

    PhotographerService photographerService;

    @Operation(summary = "Получение информации о фотографах")
    @GetMapping("/photographer/info")
    public PhotographerInfoResponse info(@Parameter(hidden = true) @AuthenticationPrincipal UmnUserDetails userDetails) {
        return photographerService.info(userDetails);
    }

    @Operation(summary = "Обновление информации")
    @PostMapping("/photographer")
    public void update(@Parameter(hidden = true) @AuthenticationPrincipal UmnUserDetails userDetails, @RequestBody PhotographerUpdateRequest request) {
        photographerService.update(userDetails, request);
    }
}
