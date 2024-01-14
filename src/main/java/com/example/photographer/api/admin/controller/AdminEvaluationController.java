package com.example.photographer.api.admin.controller;

import com.example.photographer.service.AdminEvaluationService;
import com.example.photographer.service.dto.AdminListResponse;
import com.example.photographer.service.dto.evaluation.request.AdminEvaluationFilter;
import com.example.photographer.service.dto.evaluation.request.AdminEvaluationRequest;
import com.example.photographer.service.dto.evaluation.response.AdminEvaluationResponse;
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
public class AdminEvaluationController {

    AdminEvaluationService adminEvaluationService;

    @GetMapping("/evaluation/all")
    public AdminListResponse<AdminEvaluationResponse> findAll(@ParameterObject AdminEvaluationFilter filter, @ParameterObject Pageable pageable) {
        return adminEvaluationService.findAll(filter, pageable);
    }

    @GetMapping("/evaluation/{id}")
    public AdminEvaluationResponse find(@PathVariable Long id) {
        return adminEvaluationService.find(id);
    }

    @PutMapping("/evaluation/{id}")
    public void update(@PathVariable Long id, @RequestBody AdminEvaluationRequest request) {
        adminEvaluationService.update(id, request);
    }

    @DeleteMapping("/evaluation/{id}")
    public void delete(@PathVariable Long id) {
        adminEvaluationService.delete(id);
    }
}
