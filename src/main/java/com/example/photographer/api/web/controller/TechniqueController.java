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
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "Получение списка всей техники фотографа")
    @GetMapping("/technique/all")
    public TechniqueDto findAll(@Parameter(hidden = true) @AuthenticationPrincipal UmnUserDetails userDetails) {
        return photographerTechniqueService.findAllPhotographerTechnique(userDetails);
    }

    @Operation(summary = "Обновление/создание/удаление техники фотографа",
            description = "Полученая в запросе техника будет перезаписана, удалятятся те записи, которые не пришли, " +
                    "обновятся записи, если по ним пришел айди и создадутся новые если запись не найдена или айди не указан")
    @PostMapping("/technique")
    public void updateTechnique(@Parameter(hidden = true) @AuthenticationPrincipal UmnUserDetails userDetails, @RequestBody TechniqueRequest technique) {
        photographerTechniqueService.updateTechnique(userDetails, technique);
    }
}
