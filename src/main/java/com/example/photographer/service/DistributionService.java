package com.example.photographer.service;

import com.example.photographer.service.dto.activity.distribution.request.AdminDistributionRequest;
import com.example.photographer.service.dto.activity.distribution.response.AdminDistributionResponse;
import com.example.photographer.support.UmnUserDetails;

import java.util.List;

public interface DistributionService {

    void distribute(UmnUserDetails admin, AdminDistributionRequest request);

    void check(UmnUserDetails admin, AdminDistributionRequest request);

    List<AdminDistributionResponse> ping(UmnUserDetails admin);

    void clear(List<Long> ids);
}
