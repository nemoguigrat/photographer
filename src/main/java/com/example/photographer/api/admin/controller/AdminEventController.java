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
public class AdminEventController {

    @GetMapping("/event/all")
    void findAll() {

    }

    @GetMapping("/event/{id}")
    void find(@PathVariable String id) {

    }

    @PostMapping("/event")
    void create() {

    }

    @PutMapping("/event/{id}")
    void update(@PathVariable String id) {

    }

    @DeleteMapping("/event")
    void delete() {

    }
}
