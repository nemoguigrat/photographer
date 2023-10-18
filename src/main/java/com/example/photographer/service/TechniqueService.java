package com.example.photographer.service;

import com.example.photographer.domain.TechniqueInfo;
import com.example.photographer.service.dto.technique.AbstractTechniqueRequest;
import com.example.photographer.service.dto.technique.AbstractTechniqueDto;
import com.example.photographer.service.dto.technique.request.TechniqueRequest;

import java.util.List;

public interface TechniqueService<T> {

    List<AbstractTechniqueDto> findAllTechnique();

    AbstractTechniqueDto findTechnique(Long id);

    void upsertTechnique(AbstractTechniqueRequest request);

    void deleteTechnique(Long id);

    void updateTechniqueInfo(TechniqueInfo techniqueInfo, TechniqueRequest techniqueRequest);
}
