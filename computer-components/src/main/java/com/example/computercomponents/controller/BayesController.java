package com.example.computercomponents.controller;

import com.example.computercomponents.constants.NodeTypes;
import com.example.computercomponents.controller.dto.BayesResponseDTO;
import com.example.computercomponents.service.BayesGetService;
import com.example.computercomponents.service.BayesQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/bayes")
public class BayesController {

    private final BayesQueryService bayesQueryService;
    private final BayesGetService bayesGetService;

    @Autowired
    public BayesController(BayesQueryService bayesQueryService, BayesGetService bayesGetService) {
        this.bayesQueryService = bayesQueryService;
        this.bayesGetService = bayesGetService;
    }

    @PostMapping("/{symptom}")
    public ResponseEntity<List<BayesResponseDTO>> createBayesQuery(@PathVariable String symptom){
        List<BayesResponseDTO> response;
        try{
             response = bayesQueryService.createQuery(symptom);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<String>> getSymptoms(){
        List<String> response;
        try{
            response = bayesGetService.getSpecificNodes("",NodeTypes.SYMPTOM_SUFFIX);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{symptom}")
    public ResponseEntity<List<String>> getAvailableSymptoms(@PathVariable String symptom){
        List<String> response;
        try{
            response = bayesGetService.getSpecificNodes(symptom, NodeTypes.SYMPTOM_SUFFIX);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/causes/{symptom}")
    public ResponseEntity<List<String>> getAvailableCauses(@PathVariable String symptom){
        List<String> response;
        try{
            response = bayesGetService.getSpecificNodes(symptom, NodeTypes.CAUSE_SUFFIX);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
