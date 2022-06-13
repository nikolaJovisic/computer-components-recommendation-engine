package com.example.computercomponents.controller;

import com.example.computercomponents.service.ComponentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/properties")
public class ComponentPropertiesComponent {

    private final ComponentService componentService;

    @Autowired
    public ComponentPropertiesComponent(ComponentService componentService) {
        this.componentService = componentService;
    }

    @GetMapping("/{componentName}/{propertyName}")
    ResponseEntity<List<String>> getPropertyValue(@PathVariable String componentName, @PathVariable String propertyName){
        var response = componentService.getComponentProperty(componentName,propertyName);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/better/{componentName}/{dataProperty}")
    public ResponseEntity<List<String>> getBetterComponents(@PathVariable String componentName,@PathVariable String dataProperty){
        var response = componentService.getBetterComponents(componentName, dataProperty);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
