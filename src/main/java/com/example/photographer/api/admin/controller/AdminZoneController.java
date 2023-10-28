package com.example.photographer.api.admin.controller;

import com.example.photographer.support.api.AdminApi;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import static lombok.AccessLevel.PRIVATE;

@Deprecated
@AdminApi
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class AdminZoneController {

    @GetMapping("/zone/all")
    void findAll() {

    }

    @GetMapping("/zone/{id}")
    void find(@PathVariable String id) {

    }

    @PostMapping("/zone")
    void create() {

    }

    @PutMapping("/zone/{id}")
    void update(@PathVariable String id) {

    }

    @DeleteMapping("/zone")
    void delete() {

    }
}
