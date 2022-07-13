package com.example.computercomponents.service;

import com.example.computercomponents.model.ComponentDescription;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Service;
import ucm.gaia.jcolibri.cbrcore.CBRCase;
import ucm.gaia.jcolibri.cbrcore.CaseBaseFilter;
import ucm.gaia.jcolibri.cbrcore.Connector;
import ucm.gaia.jcolibri.exception.InitializingException;
import ucm.gaia.jcolibri.util.FileIO;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Collection;
import java.util.LinkedList;
@Service
public class EvaluationService implements Connector{


    public EvaluationService() {
        retrieveAllCases();
    }

    @Override
    public Collection<CBRCase> retrieveAllCases() {
        LinkedList<CBRCase> cases = new LinkedList<CBRCase>();

        try {
            var path = TypeReference.class.getResource(com.example.computercomponents.constants.URL.CSV_PATH).toURI().getPath() + "/test.csv";
            BufferedReader br = new BufferedReader(new InputStreamReader(FileIO.openFile(path.substring(1))));
            if (br == null)
                throw new Exception("Error opening file");

            String line = "";
            while ((line = br.readLine()) != null) {
                if (line.startsWith("#") || (line.length() == 0))
                    continue;
                String[] values = line.split(",");



                CBRCase cbrCase = new CBRCase();

                ComponentDescription componentDescription = new ComponentDescription();


                // TODO
                componentDescription.setCpuName(values[2].substring(1,values[2].length()-1));
                componentDescription.setGpuName(values[4].substring(1,values[4].length()-1));
                componentDescription.setRamName(values[6].substring(1,values[6].length()-1));
                componentDescription.setStorageName(values[8].substring(1,values[8].length()-1));
                componentDescription.setThreadNum(Integer.parseInt(values[10].substring(1,values[10].length()-1)));
                componentDescription.setBaseClock(Double.parseDouble(values[1].substring(1,values[1].length()-1)));
                componentDescription.setRamSize(Integer.parseInt(values[7].substring(1,values[7].length()-1)));
                componentDescription.setDDRClass(Integer.parseInt(values[0].substring(1,values[0].length()-1)));
                componentDescription.setGpuHashRate(Double.parseDouble(values[3].substring(1,values[3].length()-1)));
                componentDescription.setGpuSize(Integer.parseInt(values[5].substring(1,values[5].length()-1)));
                componentDescription.setStorageSize(Integer.parseInt(values[9].substring(1,values[9].length()-1)));



                cbrCase.setDescription(componentDescription);
                cases.add(cbrCase);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cases;
    }

    @Override
    public Collection<CBRCase> retrieveSomeCases(CaseBaseFilter arg0) {
        return null;
    }

    @Override
    public void storeCases(Collection<CBRCase> arg0) {
    }

    @Override
    public void close() {
    }

    @Override
    public void deleteCases(Collection<CBRCase> arg0) {
    }

    @Override
    public void initFromXMLfile(URL arg0) throws InitializingException {
    }
}
