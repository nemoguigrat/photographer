package com.example.photographer.service.impl;

import com.example.photographer.domain.Model;
import com.example.photographer.exception.NotFoundException;
import com.example.photographer.repository.ModelRepository;
import com.example.photographer.service.ModelService;
import com.example.photographer.service.dto.technique.ModelDto;
import com.example.photographer.support.TechniqueType;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ModelServiceImpl implements ModelService {

    ModelRepository modelRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ModelDto> findModelByType(TechniqueType type) {
        return modelRepository.findAllByType(type).stream().map(Model::buildDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void upsertModel(ModelDto modelDto) {
        Model model;

        if (modelDto.getId() != null) {
            model = modelRepository.findById(modelDto.getId())
                    .orElseThrow(() -> new NotFoundException(modelDto.getId()));
        } else {
            model = new Model();
        }

        model.setName(modelDto.getName());
        model.setType(modelDto.getType());

        modelRepository.save(model);
    }

    @Override
    @Transactional
    public void deleteModel(Long id) {
        modelRepository.deleteById(id);
    }
}
