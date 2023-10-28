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
public class AdminLocationController {

    @GetMapping("/location/all")
    void findAll() {

    }

    @GetMapping("/location/{id}")
    void find(@PathVariable String id) {

    }

    @PostMapping("/location")
    void create() {

    }

    @PutMapping("/location/{id}")
    void update(@PathVariable String id) {

    }

    @DeleteMapping("/location")
    void delete() {

    }
}
