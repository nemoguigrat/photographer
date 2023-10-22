package com.example.photographer.service.impl;

import com.example.photographer.domain.Manufacturer;
import com.example.photographer.exception.NotFoundException;
import com.example.photographer.repository.ManufacturerRepository;
import com.example.photographer.service.ManufacturerService;
import com.example.photographer.service.dto.technique.ManufacturerDto;
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
public class ManufacturerServiceImpl implements ManufacturerService {

    ManufacturerRepository manufacturerRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ManufacturerDto> findManufacturerByType(TechniqueType type) {
        return manufacturerRepository.findAllByType(type).stream().map(Manufacturer::buildDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void upsertManufacturer(ManufacturerDto manufacturerDto) {
        Manufacturer manufacturer;

        if (manufacturerDto.getId() != null) {
            manufacturer = manufacturerRepository.findById(manufacturerDto.getId())
                    .orElseThrow(() -> new NotFoundException(manufacturerDto.getId()));
        } else {
            manufacturer = new Manufacturer();
        }

        manufacturer.setName(manufacturerDto.getName());
        manufacturer.setType(manufacturerDto.getType());

        manufacturerRepository.save(manufacturer);
    }

    @Override
    @Transactional
    public void deleteManufacturer(Long id) {
        manufacturerRepository.deleteById(id);
    }
}
