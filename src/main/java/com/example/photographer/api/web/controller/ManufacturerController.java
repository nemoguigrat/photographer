package com.example.photographer.api.web.controller;

import com.example.photographer.service.ManufacturerService;
import com.example.photographer.service.dto.technique.ManufacturerDto;
import com.example.photographer.support.TechniqueType;
import com.example.photographer.support.api.WebApi;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@WebApi
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ManufacturerController {

    ManufacturerService manufacturerService;

    //Manufacturer
    @Operation(summary = "Получение списка производителей по типу")
    @GetMapping("/technique/manufacturer/{type}")
    public List<ManufacturerDto> findManufacturerByType(@PathVariable TechniqueType type) {
        return manufacturerService.findManufacturerByType(type);
    }

    @Operation(summary = "Обновление или создание нового производителя")
    @PostMapping("/technique/manufacturer")
    public void upsertManufacturer(@RequestBody ManufacturerDto manufacturerDto) {
        manufacturerService.upsertManufacturer(manufacturerDto);
    }

    @Deprecated
    @Operation(summary = "Удаление производителя")
    @DeleteMapping("/technique/manufacturer/{id}")
    public void deleteManufacturer(@PathVariable Long id) {
        manufacturerService.deleteManufacturer(id);
    }
}
