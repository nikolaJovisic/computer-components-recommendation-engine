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
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;
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
                String[] values = line.split(";");

                CBRCase cbrCase = new CBRCase();

                ComponentDescription componentDescription = new ComponentDescription();


                // TODO
                componentDescription.setCpuName(values[0]);
                componentDescription.setGpuName(values[1]);
                componentDescription.setRamName(values[2]);
                componentDescription.setStorageName(values[3]);
                componentDescription.setThreadNum(Integer.parseInt(values[4]));
                componentDescription.setBaseClock(Double.parseDouble(values[5]));
                componentDescription.setRamSize(Integer.parseInt(values[6]));
                componentDescription.setDDRClass(Integer.parseInt(values[7]));
                componentDescription.setGpuHashRate(Double.parseDouble(values[8]));
                componentDescription.setGpuSize(Integer.parseInt(values[9]));
                componentDescription.setStorageSize(Integer.parseInt(values[10]));



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
