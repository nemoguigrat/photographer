package com.example.photographer.service;

import com.example.photographer.service.dto.technique.ModelDto;
import com.example.photographer.support.TechniqueType;

import java.util.List;

public interface ModelService {

    List<ModelDto> findModelByType(TechniqueType type);

    void upsertModel(ModelDto model);

    void deleteModel(Long id);
}
