package com.example.photographer.api.admin.controller;

import com.example.photographer.service.TechniqueResolverService;
import com.example.photographer.service.dto.photographer.response.AdminTechniqueList;
import com.example.photographer.service.dto.technique.AbstractTechniqueRequest;
import com.example.photographer.service.dto.technique.AbstractTechniqueDto;
import com.example.photographer.service.dto.technique.request.TechniqueFilter;
import com.example.photographer.service.dto.technique.response.*;
import com.example.photographer.support.TechniqueType;
import com.example.photographer.support.api.AdminApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import static lombok.AccessLevel.PRIVATE;

@AdminApi
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class AdminTechniqueController {

    TechniqueResolverService techniqueResolverService;

    @Operation(summary = "Список всей техники по типу")
    @GetMapping("/technique/{type}")
    @ApiResponses(value = {
            @ApiResponse(content = {
                    @Content(array = @ArraySchema(schema = @Schema(oneOf = {CameraDto.class, LensDto.class, BatteryDto.class, MemoryDto.class, FlashDto.class})))
            })
    })
    public AdminTechniqueList findAllByType(@ParameterObject TechniqueFilter filter, @PathVariable TechniqueType type, @ParameterObject Pageable pageable) {
        return techniqueResolverService.getServiceByType(type).findAllTechnique(filter, pageable);
    }

    @Operation(summary = "Получение объекта техниники по типу")
    @GetMapping("/technique/{type}/{id}")
    @ApiResponse(content = @Content(
            schema = @Schema(oneOf = {CameraDto.class, LensDto.class, BatteryDto.class, MemoryDto.class, FlashDto.class}))
    )
    public AbstractTechniqueDto findByType(@PathVariable TechniqueType type, @PathVariable Long id) {
        return techniqueResolverService.getServiceByType(type).findTechnique(id);
    }

    @Operation(summary = "Обновление информации о технике")
    @PutMapping("/technique/{type}")
    public void upsertByType(@PathVariable TechniqueType type, @RequestBody AbstractTechniqueRequest request) {
        techniqueResolverService.getServiceByType(type).updateTechnique(request);
    }

    @Operation(summary = "Удаление техники по типу")
    @DeleteMapping("/technique/{type}/{id}")
    public void deleteByType(@PathVariable TechniqueType type, @PathVariable Long id) {
        techniqueResolverService.getServiceByType(type).deleteTechnique(id);
    }
}
