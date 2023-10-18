package com.example.photographer.api.web.controller;

import com.example.photographer.service.ManufacturerService;
import com.example.photographer.service.ModelService;
import com.example.photographer.service.PhotographerTechniqueService;
import com.example.photographer.service.dto.technique.request.TechniqueRequest;
import com.example.photographer.service.dto.technique.ManufacturerDto;
import com.example.photographer.service.dto.technique.ModelDto;
import com.example.photographer.service.dto.technique.response.TechniqueDto;
import com.example.photographer.support.TechniqueType;
import com.example.photographer.support.UmnUserDetails;
import com.example.photographer.support.api.WebApi;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@WebApi
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class TechniqueController {

    PhotographerTechniqueService photographerTechniqueService;
    ManufacturerService manufacturerService;
    ModelService modelService;

    @GetMapping("/technique/all")
    public TechniqueDto findAll(@Parameter(hidden = true) @AuthenticationPrincipal UmnUserDetails userDetails) {
        return photographerTechniqueService.findAllPhotographerTechnique(userDetails);
    }

    @PostMapping("/technique")
    public void updateTechnique(@Parameter(hidden = true) @AuthenticationPrincipal UmnUserDetails userDetails, @RequestBody TechniqueRequest technique) {
        photographerTechniqueService.updateTechnique(userDetails, technique);
    }

    //Manufacturer
    @GetMapping("/technique/manufacturer/{type}")
    public List<ManufacturerDto> findManufacturerByType(@PathVariable TechniqueType type) {
        return manufacturerService.findManufacturerByType(type);
    }

    @PostMapping("/technique/manufacturer")
    public void upsertManufacturer(@RequestBody ManufacturerDto manufacturerDto) {
        manufacturerService.upsertManufacturer(manufacturerDto);
    }

    @DeleteMapping("/technique/manufacturer")
    public void deleteManufacturer(Long id) {
        manufacturerService.deleteManufacturer(id);
    }


    //Model
    @GetMapping("/technique/model/{type}")
    public List<ModelDto> findModelByType(@PathVariable TechniqueType type) {
        return modelService.findModelByType(type);
    }

    @PostMapping("/technique/model")
    public void upsertModel(@RequestBody ModelDto modelDto) {
        modelService.upsertModel(modelDto);
    }

    @DeleteMapping("/technique/model")
    public void deleteModel(Long id) {
        modelService.deleteModel(id);
    }
}
