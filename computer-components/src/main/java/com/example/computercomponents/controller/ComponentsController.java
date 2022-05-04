package com.example.computercomponents.controller;

import com.example.computercomponents.service.ComponentsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/components")
public class ComponentsController {

    private final ComponentsService componentsService;

    public ComponentsController(ComponentsService componentsService) {
        this.componentsService = componentsService;
    }

    @GetMapping()
    public ResponseEntity<String> getComponent() {
        ComponentsService.Exec();
        return ResponseEntity.ok("Hello world");
    }
}
