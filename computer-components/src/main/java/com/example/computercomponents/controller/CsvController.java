package com.example.computercomponents.controller;

import com.example.computercomponents.service.GenerateCsvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/api/csv")
public class CsvController {

    private final GenerateCsvService generateCsvService;

    @Autowired
    public CsvController(GenerateCsvService generateCsvService) {
        this.generateCsvService = generateCsvService;
    }

    @GetMapping()
    public void GenerateCsv(){
        generateCsvService.generateCsv();
    }
}
