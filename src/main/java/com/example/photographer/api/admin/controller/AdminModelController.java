package com.example.photographer.api.admin.controller;

import com.example.photographer.service.ModelService;
import com.example.photographer.service.dto.technique.ModelDto;
import com.example.photographer.support.TechniqueType;
import com.example.photographer.support.api.AdminApi;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AdminApi
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AdminModelController {

    ModelService modelService;

    //Model
    @Operation(summary = "Получение списка моделей по типу")
    @GetMapping("/technique/model/{type}")
    public List<ModelDto> findModelByType(@PathVariable TechniqueType type) {
        return modelService.findModelByType(type);
    }

    @Operation(summary = "Обновление или создание модели техники")
    @PostMapping("/technique/model")
    public void upsertModel(@RequestBody ModelDto modelDto) {
        modelService.upsertModel(modelDto);
    }

    @Operation(summary = "Удаление записи о модели")
    @DeleteMapping("/technique/model/{id}")
    public void deleteModel(@PathVariable Long id) {
        modelService.deleteModel(id);
    }
}
