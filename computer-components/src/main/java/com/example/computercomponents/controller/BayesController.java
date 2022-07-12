package com.example.computercomponents.controller;

import com.example.computercomponents.controller.dto.BayesResponseDTO;
import com.example.computercomponents.service.BayesQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/bayes")
public class BayesController {

    private final BayesQueryService bayesQueryService;

    @Autowired
    public BayesController(BayesQueryService bayesQueryService) {
        this.bayesQueryService = bayesQueryService;
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
}
