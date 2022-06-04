package com.example.computercomponents.controller;

import com.example.computercomponents.controller.dto.FuzzyQueryDTO;
import com.example.computercomponents.controller.dto.FuzzyResponseDTO;
import com.example.computercomponents.service.FuzzyQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/fuzzy")
public class FuzzyLogicController {
    private final FuzzyQueryService fuzzyQueryService;

    @Autowired
    public FuzzyLogicController(FuzzyQueryService fuzzyQueryService) {
        this.fuzzyQueryService = fuzzyQueryService;
    }

    @PostMapping()
    public ResponseEntity<List<FuzzyResponseDTO>> performFuzzyQuery(@RequestBody FuzzyQueryDTO fuzzyQueryDTO){
        var response = fuzzyQueryService.performQuery(fuzzyQueryDTO.getThreadNumber(),fuzzyQueryDTO.getGpuHashRate(),fuzzyQueryDTO.getRamSize(),fuzzyQueryDTO.getStorageSize(),fuzzyQueryDTO.getGpuSize());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
