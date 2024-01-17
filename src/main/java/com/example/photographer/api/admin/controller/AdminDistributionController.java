package com.example.photographer.api.admin.controller;

import com.example.photographer.service.DistributionService;
import com.example.photographer.service.dto.activity.distribution.request.AdminDistributionRequest;
import com.example.photographer.service.dto.activity.distribution.response.AdminDistributionResponse;
import com.example.photographer.support.UmnUserDetails;
import com.example.photographer.support.api.AdminApi;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@AdminApi
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class AdminDistributionController {

    DistributionService distributionService;

    @PostMapping("/service/distribute")
    public void distribute(@Parameter(hidden = true) @AuthenticationPrincipal UmnUserDetails admin, @RequestBody AdminDistributionRequest request) {
        distributionService.distribute(admin, request);
    }

    @PostMapping("/service/check")
    public void check(@Parameter(hidden = true) @AuthenticationPrincipal UmnUserDetails admin, @RequestBody AdminDistributionRequest request) {
        distributionService.check(admin, request);
    }

    @GetMapping("/service/events")
    public List<AdminDistributionResponse> ping(@Parameter(hidden = true) @AuthenticationPrincipal UmnUserDetails admin) {
        return distributionService.ping(admin);
    }
}
