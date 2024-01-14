package com.example.photographer.service.impl;

import com.example.photographer.config.properties.PhotographerProperties;
import com.example.photographer.domain.PhotographerEvaluation;
import com.example.photographer.exception.NotFoundException;
import com.example.photographer.repository.PhotographerEvaluationRepository;
import com.example.photographer.repository.specification.EvaluationSpec;
import com.example.photographer.service.AdminEvaluationService;
import com.example.photographer.service.dto.AdminListResponse;
import com.example.photographer.service.dto.evaluation.request.AdminEvaluationFilter;
import com.example.photographer.service.dto.evaluation.request.AdminEvaluationRequest;
import com.example.photographer.service.dto.evaluation.response.AdminEvaluationResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AdminEvaluationServiceImpl implements AdminEvaluationService {

    PhotographerEvaluationRepository evaluationRepository;

    @Override
    @Transactional(readOnly = true)
    public AdminListResponse<AdminEvaluationResponse> findAll(AdminEvaluationFilter filter, Pageable pageable) {
        Page<PhotographerEvaluation> evaluations = evaluationRepository.findAll(EvaluationSpec.filter(filter), pageable);

        return AdminListResponse.of(evaluations.map(AdminEvaluationResponse::new));
    }

    @Override
    @Transactional(readOnly = true)
    public AdminEvaluationResponse find(Long id) {
        PhotographerEvaluation evaluation = evaluationRepository.findOne(EvaluationSpec.findById(id)).orElseThrow(() -> new NotFoundException(id));

        return new AdminEvaluationResponse(evaluation);
    }

    @Override
    @Transactional
    public void update(Long id, AdminEvaluationRequest request) {
        PhotographerEvaluation evaluation = evaluationRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        evaluation.update(request);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        evaluationRepository.deleteById(id);
    }
}
