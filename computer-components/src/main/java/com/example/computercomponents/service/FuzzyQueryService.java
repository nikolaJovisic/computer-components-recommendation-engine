package com.example.computercomponents.service;

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

    public FuzzyQueryService() throws URISyntaxException {
        var path = TypeReference.class.getResource(URL.FUZZY_LOGIC_PATH).toURI().getPath();
        this.fis = FIS.load(path, true);
        if (fis != null)
            System.out.println("Fuzzy rules successfully loaded!");

        computerUsages = Arrays.asList("homeUsage","miningUsage","gamingUsage","programmingUsage");
        blockName = "sablon";
    }

    public List<FuzzyResponseDTO> performQuery(int threadNumber, int gpuHashRate, int ramSize, int storageSize, int gpuSize) {
        fis.setVariable("threadNumber", threadNumber);
        fis.setVariable("ramSize", ramSize);
        fis.setVariable("gpuSize", gpuSize);
        fis.setVariable("storageSize", storageSize);
        fis.setVariable("gpuHashRate", gpuHashRate);
        fis.evaluate();

        var response = new ArrayList<FuzzyResponseDTO>();
        for(var usage : computerUsages)
        {
            var percentage = fis.getFunctionBlock(blockName).getVariable(usage);
            response.add(new FuzzyResponseDTO(usage,percentage.getValue()));
        }

        return response;
    }
}
