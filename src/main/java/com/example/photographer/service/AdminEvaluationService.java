package com.example.photographer.service;

import com.example.photographer.service.dto.AdminListResponse;
import com.example.photographer.service.dto.evaluation.request.AdminEvaluationFilter;
import com.example.photographer.service.dto.evaluation.request.AdminEvaluationRequest;
import com.example.photographer.service.dto.evaluation.response.AdminEvaluationResponse;
import org.springframework.data.domain.Pageable;

public interface AdminEvaluationService {

    AdminListResponse<AdminEvaluationResponse> findAll(AdminEvaluationFilter filter, Pageable pageable);

    AdminEvaluationResponse find(Long id);

    void update(Long id, AdminEvaluationRequest request);

    void delete(Long id);
}
