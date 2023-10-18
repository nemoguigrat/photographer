package com.example.photographer.service.impl;

import com.example.photographer.service.dto.technique.AbstractTechniqueRequest;
import com.example.photographer.domain.Manufacturer;
import com.example.photographer.domain.Model;
import com.example.photographer.repository.BaseTechniqueRepository;
import com.example.photographer.repository.ManufacturerRepository;
import com.example.photographer.repository.ModelRepository;
import com.example.photographer.service.TechniqueService;
import com.example.photographer.service.dto.technique.AbstractTechniqueDto;
import com.example.photographer.support.domain.AbstractTechnique;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public abstract class AbstractTechniqueService<T extends AbstractTechnique> implements TechniqueService<T> {

    BaseTechniqueRepository<T> repository;

    ManufacturerRepository manufacturerRepository;
    ModelRepository modelRepository;

    @Override
    public List<AbstractTechniqueDto> findAllTechnique() {
        return repository.findAll().stream().map(T::buildDto).collect(Collectors.toList());
    }

    @Override
    public AbstractTechniqueDto findTechnique(Long id) {
        Optional<T> technique = repository.findById(id);
        return technique.map(T::buildDto).orElse(null);

    }

    @Override
    public void upsertTechnique(AbstractTechniqueRequest request) {
    }

    @Override
    public void deleteTechnique(Long id) {
        repository.deleteById(id);
    }

    protected final void applyModelAndManufacturer(T domain, AbstractTechniqueRequest request) {
        Manufacturer manufacturer = null;
        Model model = null;

        if (request.getManufacturerId() != null) {
            manufacturer = manufacturerRepository.findById(request.getManufacturerId()).orElse(null);
        }

        if (request.getModelId() != null) {
            model = modelRepository.findById(request.getModelId()).orElse(null);
        }

        domain.setManufacturer(manufacturer);
        domain.setModel(model);
    }
}
