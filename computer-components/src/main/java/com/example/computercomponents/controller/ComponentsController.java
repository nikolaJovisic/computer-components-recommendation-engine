package com.example.computercomponents.controller;

import com.example.computercomponents.service.ComponentService;
import com.example.computercomponents.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/components")
public class ComponentsController {


    private final ComponentService componentservice;
    private final QueryService queryService;

    @Autowired
    public ComponentsController(ComponentService componentservice, QueryService queryService) {
        this.componentservice = componentservice;
        this.queryService = queryService;
    }


    @GetMapping()
    public ResponseEntity<List<String>> performQuery(@RequestBody String query){
        var response = queryService.executeQuery(query);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{componentName}")
    public ResponseEntity<List<String>> getComponents(@PathVariable String componentName){
        var response = componentservice.getComponents(componentName);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/motherboards/{componentType}")
    public ResponseEntity<List<String>> getCompatibleMotherboards(@RequestBody String componentName,@PathVariable String componentType){
        var response = componentservice.getCompatibleMotherboards(componentName, componentType);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/{componentName}/{dataProperty}")
    public ResponseEntity<List<String>> getBetterComponents(@PathVariable String componentName,@PathVariable String dataProperty){
        var response = componentservice.getBetterComponents(componentName, dataProperty);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/compatible/{component}")
    public ResponseEntity<List<String>> getCompatibleComponents(@PathVariable String component,@RequestBody String motherboard){
        var rams = componentservice.getMotherboardCompatibleComponents(component,motherboard);
        return new ResponseEntity<>(rams, HttpStatus.OK);
    }
}
