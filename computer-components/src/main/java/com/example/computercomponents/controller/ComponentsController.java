package com.example.computercomponents.controller;

import com.example.computercomponents.service.ComponentsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/components")
public class ComponentsController {

    private final ComponentsService componentsService;

    public ComponentsController(ComponentsService componentsService) {
        this.componentsService = componentsService;
    }

    @PostMapping()
    public ResponseEntity<String> getComponent(@RequestBody String query) {
        componentsService.LoadOntology(query);
        return ResponseEntity.ok("Hello world");
    }
}
