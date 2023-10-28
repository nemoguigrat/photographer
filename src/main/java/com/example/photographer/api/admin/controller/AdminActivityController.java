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
public class AdminActivityController {

    @GetMapping("/activity/all")
    void findAll() {

    }

    @GetMapping("/activity/{id}")
    void find(@PathVariable String id) {

    }

    @PostMapping("/activity")
    void create() {

    }

    @PutMapping("/activity/{id}")
    void update(@PathVariable String id) {

    }

    @DeleteMapping("/activity")
    void delete() {

    }
}
