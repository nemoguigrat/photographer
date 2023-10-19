package com.example.photographer.api.admin.controller;

import com.example.photographer.service.TechniqueResolverService;
import com.example.photographer.service.dto.technique.AbstractTechniqueRequest;
import com.example.photographer.service.dto.technique.AbstractTechniqueDto;
import com.example.photographer.service.impl.AbstractTechniqueService;
import com.example.photographer.support.TechniqueType;
import com.example.photographer.support.api.AdminApi;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@AdminApi
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class AdminTechniqueController {

    TechniqueResolverService techniqueResolverService;

    @Operation(summary = "Список всей техники по типу")
    @GetMapping("/technique/{type}")
    public List<AbstractTechniqueDto> findAllByType(@PathVariable TechniqueType type) {
        return techniqueResolverService.getServiceByType(type).findAllTechnique();
    }

    @Operation(summary = "Получение объекта техниники по типу")
    @GetMapping("/technique/{type}/{id}")
    public AbstractTechniqueDto findByType(@PathVariable TechniqueType type, @PathVariable Long id) {
        return techniqueResolverService.getServiceByType(type).findTechnique(id);
    }

    @Deprecated
    @PostMapping("/technique/{type}")
    public void upsertByType(@PathVariable TechniqueType type, @RequestBody AbstractTechniqueRequest request) {
        techniqueResolverService.getServiceByType(type).upsertTechnique(request);
    }

    @Operation(summary = "Удаление техники по типу", description = "Метод реализован, но не оттестирован, возможны ошибки")
    @DeleteMapping("/technique/{type}/{id}")
    public void deleteByType(@PathVariable TechniqueType type, @PathVariable Long id) {
        techniqueResolverService.getServiceByType(type).deleteTechnique(id);
    }
}
