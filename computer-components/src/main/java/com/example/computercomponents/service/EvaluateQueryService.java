package com.example.computercomponents.service;

import com.example.computercomponents.constants.OntologyProperties;
import com.example.computercomponents.model.ComponentDescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ucm.gaia.jcolibri.cbraplications.StandardCBRApplication;
import ucm.gaia.jcolibri.cbrcore.CBRQuery;

@Service
public class EvaluateQueryService {

    private final ComponentService componentService;

    @Autowired
    public EvaluateQueryService(ComponentService componentService) {
        this.componentService = componentService;
    }

    public void Evaluate(String cpuName, String gpuName, String storageName, String ramName){
        var threadNumber = Integer.parseInt(componentService.getComponentProperty(cpuName, OntologyProperties.THREAD_NUM).get(0));
        var baseClock = Double.parseDouble(componentService.getComponentProperty(cpuName, OntologyProperties.BASE_CLOCK).get(0));
        var gpuHashRate = Double.parseDouble(componentService.getComponentProperty(gpuName, OntologyProperties.HASH_RATE).get(0));
        var ramSize = Integer.parseInt(componentService.getComponentProperty(ramName, OntologyProperties.STORAGE_SIZE).get(0));
        var storageSize = Integer.parseInt(componentService.getComponentProperty(storageName, OntologyProperties.STORAGE_SIZE).get(0));
        var gpuSize = Integer.parseInt(componentService.getComponentProperty(gpuName, OntologyProperties.STORAGE_SIZE).get(0));
        var DDRClass = Integer.parseInt(componentService.getComponentProperty(ramName, OntologyProperties.DDR_CLASS).get(0));

        StandardCBRApplication recommender = new EvaluationService1();

        try {
            recommender.configure();

            recommender.preCycle();

            CBRQuery query = new CBRQuery();
            ComponentDescription configurationCaseDescription = new ComponentDescription();

            // TODO
            configurationCaseDescription.setBaseClock(baseClock);
            configurationCaseDescription.setCpuName(cpuName);
            configurationCaseDescription.setDDRClass(DDRClass);
            configurationCaseDescription.setGpuHashRate(gpuHashRate);
            configurationCaseDescription.setRamName(ramName);
            configurationCaseDescription.setGpuHashRate(ramSize);
            configurationCaseDescription.setStorageSize(storageSize);
            configurationCaseDescription.setGpuName(gpuName);
            configurationCaseDescription.setThreadNum(threadNumber);
            configurationCaseDescription.setGpuSize(gpuSize);
            configurationCaseDescription.setStorageName(storageName);

            query.setDescription( configurationCaseDescription );

            recommender.cycle(query);

            recommender.postCycle();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
