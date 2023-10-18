package com.example.photographer.service;

import com.example.photographer.service.dto.technique.ManufacturerDto;
import com.example.photographer.support.TechniqueType;

import java.util.List;

public interface ManufacturerService {

    List<ManufacturerDto> findManufacturerByType(TechniqueType type);

    void upsertManufacturer(ManufacturerDto model);

    void deleteManufacturer(Long id);
}
