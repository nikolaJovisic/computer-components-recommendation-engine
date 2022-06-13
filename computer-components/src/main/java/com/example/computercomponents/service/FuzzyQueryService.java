package com.example.computercomponents.service;

import com.example.computercomponents.constants.FuzzyVariables;
import com.example.computercomponents.constants.OntologyProperties;
import com.example.computercomponents.constants.URL;
import com.example.computercomponents.controller.dto.FuzzyResponseDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import net.sourceforge.jFuzzyLogic.FIS;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class FuzzyQueryService {

    private FIS fis;
    private List<String> computerUsages;
    private String blockName;
    private final ComponentService componentService;

    public FuzzyQueryService(ComponentService componentService) throws URISyntaxException {
        this.componentService = componentService;
        var path = TypeReference.class.getResource(URL.FUZZY_LOGIC_PATH).toURI().getPath();
        this.fis = FIS.load(path, true);
        if (fis != null)
            System.out.println("Fuzzy rules successfully loaded!");

        computerUsages = Arrays.asList("homeUsage","miningUsage","gamingUsage","programmingUsage");
        blockName = "sablon";
    }

    public List<FuzzyResponseDTO> performQuery(int threadNumber, double gpuHashRate, int ramSize, int storageSize, int gpuSize) {
        fis.setVariable(FuzzyVariables.THREAD_NUM, threadNumber);
        fis.setVariable(FuzzyVariables.RAM_SIZE, ramSize);
        fis.setVariable(FuzzyVariables.GPU_SIZE, gpuSize);
        fis.setVariable(FuzzyVariables.STORAGE_SIZE, storageSize);
        fis.setVariable(FuzzyVariables.HASH_RATE, gpuHashRate);
        fis.evaluate();

        var response = new ArrayList<FuzzyResponseDTO>();
        for(var usage : computerUsages)
        {
            var percentage = fis.getFunctionBlock(blockName).getVariable(usage);
            response.add(new FuzzyResponseDTO(usage,percentage.getValue()));
        }

        return response;
    }

    public List<FuzzyResponseDTO>  performQuery(String cpuName, String gpuName, String ramName, String storageName) {
        int threadNumber;
        double gpuHashRate;
        int ramSize;
        int storageSize;
        int gpuSize;
        try {
             threadNumber = Integer.parseInt(componentService.getComponentProperty(cpuName, OntologyProperties.THREAD_NUM).get(0));
             gpuHashRate = Double.parseDouble(componentService.getComponentProperty(gpuName, OntologyProperties.HASH_RATE).get(0));
             ramSize = Integer.parseInt(componentService.getComponentProperty(ramName, OntologyProperties.STORAGE_SIZE).get(0));
             storageSize = Integer.parseInt(componentService.getComponentProperty(storageName, OntologyProperties.STORAGE_SIZE).get(0));
             gpuSize = Integer.parseInt(componentService.getComponentProperty(gpuName, OntologyProperties.STORAGE_SIZE).get(0));
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }

        return performQuery(threadNumber,gpuHashRate,ramSize,storageSize,gpuSize);
    }
}
