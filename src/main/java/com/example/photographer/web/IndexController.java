package com.example.photographer.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class IndexController {

    @GetMapping("/")
    public ResponseEntity<?> index() {
        return ResponseEntity.ok().body(Map.of("key", "1", "key3", "2"));
    }
}
