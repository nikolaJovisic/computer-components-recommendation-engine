package com.example.computercomponents.service;

import com.example.computercomponents.constants.OntologyProperties;
import com.example.computercomponents.constants.URL;
import com.example.computercomponents.model.ComponentDescription;
import com.fasterxml.jackson.core.type.TypeReference;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Writer;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
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

    public void generateCsv() throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException, URISyntaxException {
        var persistanceList = new ArrayList<ComponentDescription>();
        var rams = componentService.getComponents("RAM").stream().limit(INSTANCE_NUM).collect(Collectors.toList());
        for(var ram : rams){
            var cpus = componentService.getComponents("CPU").stream().limit(INSTANCE_NUM-1).collect(Collectors.toList());
            for(var cpu : cpus){
                var gpus = componentService.getComponents("GraphicsCard").stream().limit(INSTANCE_NUM-2).collect(Collectors.toList());
                for(var gpu : gpus) {
                    var storages = componentService.getComponents("SSD").stream().limit(INSTANCE_NUM-3).collect(Collectors.toList());
                    for(var storage : storages){
                        persistanceList.add(getDescription(ram,cpu,gpu,storage));
                    }
                }
            }
        }
        serialize(persistanceList);
    }

    private void serialize(List<ComponentDescription> components) throws IOException, URISyntaxException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
         var path = TypeReference.class.getResource(URL.CSV_PATH).toURI().getPath() + "/test.csv";
         Writer writer = Files.newBufferedWriter(Paths.get(path.substring(1)));

        StatefulBeanToCsvBuilder<ComponentDescription> builder=
                new StatefulBeanToCsvBuilder(writer);
        StatefulBeanToCsv beanWriter =
                builder.build();
        beanWriter.write(components);

        // closing the writer object
        writer.close();
    }

    private ComponentDescription getDescription(String ramName,String cpuName,String gpuName,String storageName){
        var threadNumber = Integer.parseInt(componentService.getComponentProperty(cpuName, OntologyProperties.THREAD_NUM).get(0));
        var baseClock = Double.parseDouble(componentService.getComponentProperty(cpuName, OntologyProperties.BASE_CLOCK).get(0));
        var gpuHashRate = Double.parseDouble(componentService.getComponentProperty(gpuName, OntologyProperties.HASH_RATE).get(0));
        var ramSize = Integer.parseInt(componentService.getComponentProperty(ramName, OntologyProperties.STORAGE_SIZE).get(0));
        var storageSize = Integer.parseInt(componentService.getComponentProperty(storageName, OntologyProperties.STORAGE_SIZE).get(0));
        var gpuSize = Integer.parseInt(componentService.getComponentProperty(gpuName, OntologyProperties.STORAGE_SIZE).get(0));
        var DDRClass = Integer.parseInt(componentService.getComponentProperty(ramName, OntologyProperties.DDR_CLASS).get(0));

        var description = new ComponentDescription(cpuName,gpuName,ramName,storageName,threadNumber,baseClock,ramSize,DDRClass,gpuHashRate,gpuSize,storageSize);
        System.out.println(description);
        return description;
    }
}
