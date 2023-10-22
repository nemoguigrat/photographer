package com.example.photographer.service.impl;

import com.example.photographer.exception.NotFoundException;
import com.example.photographer.service.dto.photographer.response.AdminTechniqueList;
import com.example.photographer.service.dto.technique.AbstractTechniqueRequest;
import com.example.photographer.domain.Manufacturer;
import com.example.photographer.domain.Model;
import com.example.photographer.repository.BaseTechniqueRepository;
import com.example.photographer.repository.ManufacturerRepository;
import com.example.photographer.repository.ModelRepository;
import com.example.photographer.service.TechniqueService;
import com.example.photographer.service.dto.technique.AbstractTechniqueDto;
import com.example.photographer.service.dto.technique.request.TechniqueFilter;
import com.example.photographer.support.domain.AbstractTechnique;
import com.example.photographer.support.domain.Evaluated;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

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
    public AdminTechniqueList findAllTechnique(TechniqueFilter filter, Pageable pageable) {
        return AdminTechniqueList.of(
                repository.findTechniqueWithModelAndManufacturer(pageable).map(T::buildDto)
        );
    }

    @Override
    public AbstractTechniqueDto findTechnique(Long id) {
        Optional<T> technique = repository.findById(id);
        return technique.map(T::buildDto).orElse(null);

    }

    @Override
    @Transactional
    public void updateTechnique(AbstractTechniqueRequest request) {
        T technique = repository.findById(request.getId())
                .orElseThrow(() -> new NotFoundException(request.getId()));

        if (technique instanceof Evaluated) {
            ((Evaluated) technique).setRating(request.getRating());
        }

        applyModelAndManufacturer(technique, request);
        applyFromRequest(technique, request);
    }

    @Override
    @Transactional
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

    protected abstract void applyFromRequest(T domain, AbstractTechniqueRequest request);
}
