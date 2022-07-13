package com.example.computercomponents.controller;

import com.example.computercomponents.constants.NodeTypes;
import com.example.computercomponents.controller.dto.BayesQueryDTO;
import com.example.computercomponents.controller.dto.BayesResponseDTO;
import com.example.computercomponents.service.BayesComponentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/api/bayes")
public class BayesController {

    private final BayesComponentService bayesComponentService;

    @Autowired
    public BayesController( BayesComponentService bayesComponentService) {
        this.bayesComponentService = bayesComponentService;
    }


    @PostMapping("/complexQuery")
    public ResponseEntity<List<BayesResponseDTO>> createBayesQuery(@RequestBody BayesQueryDTO bayesQueryDTO)  {
        List<BayesResponseDTO> response;
        try{
            response = bayesComponentService.getQueryResponse(bayesQueryDTO.getSymptoms(),bayesQueryDTO.getCauses());
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<String>> getSymptoms(){
        List<String> response;
        try{
            response = bayesComponentService.getSpecificNodes("",NodeTypes.SYMPTOM_SUFFIX);
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
            response = bayesComponentService.getSpecificNodes(symptom, NodeTypes.SYMPTOM_SUFFIX);
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
            response = bayesComponentService.getSpecificNodes(symptom, NodeTypes.CAUSE_SUFFIX);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
