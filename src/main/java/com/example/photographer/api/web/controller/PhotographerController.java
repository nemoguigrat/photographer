package com.example.photographer.api.web.controller;

import com.example.photographer.service.dto.photographer.request.PhotographerChangeCredentialRequest;
import com.example.photographer.service.dto.photographer.request.PhotographerUpdateRequest;
import com.example.photographer.service.dto.photographer.response.PhotographerAvatarResponse;
import com.example.photographer.service.dto.photographer.response.PhotographerInfoResponse;
import com.example.photographer.service.PhotographerService;
import com.example.photographer.support.UmnUserDetails;
import com.example.photographer.support.api.WebApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
    @PutMapping("/photographer/info")
    public void update(@Parameter(hidden = true) @AuthenticationPrincipal UmnUserDetails userDetails, @RequestBody PhotographerUpdateRequest request) {
        photographerService.update(userDetails, request);
    }

    @Operation(summary = "Обновление логина и пароля")
    @PutMapping("/photographer/credential")
    public void update(@Parameter(hidden = true) @AuthenticationPrincipal UmnUserDetails userDetails, @RequestBody PhotographerChangeCredentialRequest request) {
        photographerService.updateCredential(userDetails, request);
    }

    @Operation(summary = "Установить аватар")
    @PostMapping(value = "/photographer/image", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public void upload(@Parameter(hidden = true) @AuthenticationPrincipal UmnUserDetails userDetails,
                       @RequestPart(name = "file") MultipartFile file) {
        photographerService.upload(userDetails, file);
    }

    @Operation(summary = "Загрузить аватар")
    @GetMapping(value = "/photographer/image")
    public ResponseEntity<Resource> download(@Parameter(hidden = true) @AuthenticationPrincipal UmnUserDetails userDetails) {
        PhotographerAvatarResponse response = photographerService.download(userDetails);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf(response.getContentType()))
                .body(response.getContent());
    }
}
