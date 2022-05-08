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
        return new ResponseEntity<List<String>>(response, HttpStatus.OK);
    }

    @GetMapping("/ram")
    public ResponseEntity<List<String>> getCompatibleRAMs(@RequestBody String motherboard){
        var rams = componentservice.getRAMs(motherboard);
        return new ResponseEntity<>(rams, HttpStatus.OK);
    }
}
