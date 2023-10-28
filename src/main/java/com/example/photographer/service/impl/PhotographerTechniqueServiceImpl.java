package com.example.photographer.service.impl;

import com.example.photographer.service.TechniqueResolverService;
import com.example.photographer.service.TechniqueService;
import com.example.photographer.service.dto.technique.AbstractTechniqueDto;
import com.example.photographer.service.dto.technique.request.TechniqueRequest;
import com.example.photographer.domain.*;
import com.example.photographer.repository.PhotographerRepository;
import com.example.photographer.repository.TechniqueInfoRepository;
import com.example.photographer.service.PhotographerTechniqueService;
import com.example.photographer.service.dto.technique.response.TechniqueDto;
import com.example.photographer.support.TechniqueType;
import com.example.photographer.support.UmnUserDetails;
import com.example.photographer.support.domain.AbstractTechnique;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
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
        Photographer photographer = photographerRepository.findPhotographerById(userDetails.getId());
        TechniqueInfo techniqueInfo = techniqueInfoRepository.findAndFetchTechniqueByPhotographer(photographer);

        List<AbstractTechnique> technique = new LinkedList<>();
        technique.addAll(techniqueInfo.getCameras());
        technique.addAll(techniqueInfo.getFlashes());
        technique.addAll(techniqueInfo.getLenses());
        technique.addAll(techniqueInfo.getMemories());
        technique.addAll(techniqueInfo.getBatteries());

        return TechniqueDto.builder()
                .id(techniqueInfo.getId())
                .batteryCount(techniqueInfo.getBatteryCount())
                .laptop(techniqueInfo.isLaptop())
                .technique(technique.stream().map(AbstractTechnique::buildDto).collect(Collectors.toList()))
                .additionalTechnique(techniqueInfo.getAdditionalTechniques().stream().map(AdditionalTechnique::buildDto).collect(Collectors.toList()))
                .build();
    }

    @Override
    @Transactional
    public void updateTechnique(UmnUserDetails userDetails, TechniqueRequest technique) {
        Photographer photographer = photographerRepository.findPhotographerById(userDetails.getId());

        TechniqueInfo techniqueInfo = techniqueInfoRepository.findAndFetchTechniqueByPhotographer(photographer);

        for (TechniqueType type : TechniqueType.values()) {
            TechniqueService<? extends AbstractTechnique> service = techniqueResolverService.getServiceByType(type);

            if (service == null) {
                return;
            }

            service.updateTechniqueInfo(techniqueInfo, technique);
        }

        techniqueInfo.setLaptop(technique.isLaptop());
        techniqueInfo.setDescription(technique.getDescription());
        techniqueInfo.setBatteryCount(technique.getBatteryCount());
    }
}
