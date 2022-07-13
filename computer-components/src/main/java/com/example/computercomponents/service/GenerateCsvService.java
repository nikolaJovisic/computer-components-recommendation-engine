package com.example.computercomponents.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class GenerateCsvService {

    private final ComponentService componentService;

    private final int INSTANCE_NUM = 5;

    @Autowired
    public GenerateCsvService(ComponentService componentService) {
        this.componentService = componentService;
    }

    public void generateCsv(){
        var rams = componentService.getComponents("RAM").stream().limit(INSTANCE_NUM).collect(Collectors.toList());
        var cpu = componentService.getComponents("CPU").stream().limit(INSTANCE_NUM).collect(Collectors.toList());
        var gpu = componentService.getComponents("GraphicsCard").stream().limit(INSTANCE_NUM).collect(Collectors.toList());
        var storage = componentService.getComponents("SSD").stream().limit(INSTANCE_NUM).collect(Collectors.toList());
    }
}
