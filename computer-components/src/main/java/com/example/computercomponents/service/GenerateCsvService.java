package com.example.computercomponents.service;

import com.example.computercomponents.constants.OntologyProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class GenerateCsvService {

    private final ComponentService componentService;
    //min 4
    private final int INSTANCE_NUM = 5;

    @Autowired
    public GenerateCsvService(ComponentService componentService) {
        this.componentService = componentService;
    }

    public void generateCsv(){
        var rams = componentService.getComponents("RAM").stream().limit(INSTANCE_NUM).collect(Collectors.toList());
        for(var ram : rams){
            var cpus = componentService.getComponents("CPU").stream().limit(INSTANCE_NUM-1).collect(Collectors.toList());
            for(var cpu : cpus){
                var gpus = componentService.getComponents("GraphicsCard").stream().limit(INSTANCE_NUM-2).collect(Collectors.toList());
                for(var gpu : gpus) {
                    var storages = componentService.getComponents("SSD").stream().limit(INSTANCE_NUM-3).collect(Collectors.toList());
                    for(var storage : storages){

                    }
                }
            }
        }
    }

    private void printInfo(String ramName,String cpuName,String gpuName,String storageName){
        var threadNumber = Integer.parseInt(componentService.getComponentProperty(cpuName, OntologyProperties.THREAD_NUM).get(0));
        var baseClock = Double.parseDouble(componentService.getComponentProperty(cpuName, OntologyProperties.BASE_CLOCK).get(0));
        var gpuHashRate = Double.parseDouble(componentService.getComponentProperty(gpuName, OntologyProperties.HASH_RATE).get(0));
        var ramSize = Integer.parseInt(componentService.getComponentProperty(ramName, OntologyProperties.STORAGE_SIZE).get(0));
        var storageSize = Integer.parseInt(componentService.getComponentProperty(storageName, OntologyProperties.STORAGE_SIZE).get(0));
        var gpuSize = Integer.parseInt(componentService.getComponentProperty(gpuName, OntologyProperties.STORAGE_SIZE).get(0));
        var DDRClass = Integer.parseInt(componentService.getComponentProperty(ramName, OntologyProperties.DDR_CLASS).get(0));

    }
}
