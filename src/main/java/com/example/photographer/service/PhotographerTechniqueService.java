package com.example.photographer.service;

import com.example.photographer.service.dto.technique.request.TechniqueRequest;
import com.example.photographer.service.dto.technique.response.TechniqueDto;
import com.example.photographer.support.UmnUserDetails;

public interface PhotographerTechniqueService {

    TechniqueDto findAllPhotographerTechnique(UmnUserDetails userDetails);

    void updateTechnique(UmnUserDetails userDetails, TechniqueRequest technique);
}
