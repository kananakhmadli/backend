package com.company.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Home {

    @GetMapping
    public ResponseEntity<String> home() {
        return ResponseEntity.ok().body("Application is working");
    }

}