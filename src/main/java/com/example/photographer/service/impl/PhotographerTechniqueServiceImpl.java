package com.example.photographer.service.impl;

import com.example.photographer.service.TechniqueResolverService;
import com.example.photographer.service.dto.technique.request.TechniqueRequest;
import com.example.photographer.domain.*;
import com.example.photographer.repository.PhotographerRepository;
import com.example.photographer.repository.TechniqueInfoRepository;
import com.example.photographer.service.PhotographerTechniqueService;
import com.example.photographer.service.dto.technique.response.TechniqueDto;
import com.example.photographer.support.UmnUserDetails;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class PhotographerTechniqueServiceImpl implements PhotographerTechniqueService {

    TechniqueInfoRepository techniqueInfoRepository;
    PhotographerRepository photographerRepository;
    TechniqueResolverService techniqueResolverService;

    @Override
    @Transactional(readOnly = true)
    public TechniqueDto findAllPhotographerTechnique(UmnUserDetails userDetails) {
        Photographer photographer = photographerRepository.findByUser_Id(userDetails.getId())
                .orElseThrow(() -> new UsernameNotFoundException("msg"));
        TechniqueInfo techniqueInfo = techniqueInfoRepository.findAndFetchTechniqueByPhotographer(photographer);

        return TechniqueDto.builder()
                .id(techniqueInfo.getId())
                .batteryCount(techniqueInfo.getBatteryCount())
                .laptop(techniqueInfo.isLaptop())
                .cameras(techniqueInfo.getCameras().stream().map(Camera::buildDto).collect(Collectors.toList()))
                .flashes(techniqueInfo.getFlashes().stream().map(Flash::buildDto).collect(Collectors.toList()))
                .lenses(techniqueInfo.getLenses().stream().map(Lens::buildDto).collect(Collectors.toList()))
                .memories(techniqueInfo.getMemories().stream().map(Memory::buildDto).collect(Collectors.toList()))
                .batteries(techniqueInfo.getBatteries().stream().map(Battery::buildDto).collect(Collectors.toList()))
                .additionalTechnique(techniqueInfo.getAdditionalTechniques().stream().map(AdditionalTechnique::buildDto).collect(Collectors.toList()))
                .build();
    }

    @Override
    @Transactional
    public void updateTechnique(UmnUserDetails userDetails, TechniqueRequest technique) {
        Photographer photographer = photographerRepository.findByUser_Id(userDetails.getId())
                .orElseThrow(() -> new UsernameNotFoundException("msg"));

        TechniqueInfo techniqueInfo = techniqueInfoRepository.findAndFetchTechniqueByPhotographer(photographer);

        techniqueResolverService.getServices().forEach(
                service -> service.updateTechniqueInfo(techniqueInfo, technique)
        );
    }
}
