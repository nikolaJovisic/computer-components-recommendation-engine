package com.example.computercomponents.controller;

import com.example.computercomponents.controller.dto.FuzzyComponentQueryDTO;
import com.example.computercomponents.service.EvaluateQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/api/evaluate")
public class EvaluateController {

    private EvaluateQueryService evaluationService;

    @Autowired
    public EvaluateController(EvaluateQueryService evaluationService) {
        this.evaluationService = evaluationService;
    }

    @PostMapping()
    public void doSomethink(@RequestBody FuzzyComponentQueryDTO queryDTO){
        evaluationService.Evaluate(queryDTO.getCpuName(),queryDTO.getGpuName(),queryDTO.getStorageName(),queryDTO.getRamName());
    }

}
