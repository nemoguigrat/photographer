package com.example.photographer.service;

import com.example.photographer.domain.TechniqueInfo;
import com.example.photographer.service.dto.photographer.response.AdminTechniqueList;
import com.example.photographer.service.dto.technique.AbstractTechniqueRequest;
import com.example.photographer.service.dto.technique.AbstractTechniqueDto;
import com.example.photographer.service.dto.technique.request.TechniqueFilter;
import com.example.photographer.service.dto.technique.request.TechniqueRequest;
import org.springframework.data.domain.Pageable;

public interface TechniqueService<T> {

    AdminTechniqueList findAllTechnique(TechniqueFilter filter, Pageable pageable);

    AbstractTechniqueDto findTechnique(Long id);

    void updateTechnique(AbstractTechniqueRequest request);

    void deleteTechnique(Long id);

    void updateTechniqueInfo(TechniqueInfo techniqueInfo, TechniqueRequest techniqueRequest);
}
