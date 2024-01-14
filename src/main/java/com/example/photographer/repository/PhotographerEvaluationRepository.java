package com.example.photographer.repository;

import com.example.photographer.domain.PhotographerEvaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotographerEvaluationRepository extends JpaRepository<PhotographerEvaluation, Long>, JpaSpecificationExecutor<PhotographerEvaluation> {
}
