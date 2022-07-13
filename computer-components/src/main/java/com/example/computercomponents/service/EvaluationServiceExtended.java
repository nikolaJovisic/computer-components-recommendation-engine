package com.example.computercomponents.service;

import com.example.computercomponents.controller.dto.EvaluationResponseDTO;
import com.example.computercomponents.model.ComponentDescription;
import ucm.gaia.jcolibri.casebase.LinealCaseBase;
import ucm.gaia.jcolibri.cbraplications.StandardCBRApplication;
import ucm.gaia.jcolibri.cbrcore.*;
import ucm.gaia.jcolibri.exception.ExecutionException;
import ucm.gaia.jcolibri.method.retrieve.NNretrieval.NNConfig;
import ucm.gaia.jcolibri.method.retrieve.NNretrieval.NNScoringMethod;
import ucm.gaia.jcolibri.method.retrieve.NNretrieval.similarity.global.Average;
import ucm.gaia.jcolibri.method.retrieve.NNretrieval.similarity.local.Equal;
import ucm.gaia.jcolibri.method.retrieve.NNretrieval.similarity.local.Interval;
import ucm.gaia.jcolibri.method.retrieve.RetrievalResult;
import ucm.gaia.jcolibri.method.retrieve.selection.SelectCases;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EvaluationServiceExtended implements StandardCBRApplication {
    Connector _connector;
    CBRCaseBase _caseBase;
    NNConfig simConfig;
    public static List<EvaluationResponseDTO> response;




    public void configure() throws ExecutionException {
        _connector =  new EvaluationService();

        _caseBase = new LinealCaseBase();

        simConfig = new NNConfig();
        simConfig.setDescriptionSimFunction(new Average());
        // TODO
        simConfig.addMapping(new Attribute("threadNum", ComponentDescription.class), new Interval(4));
        simConfig.addMapping(new Attribute("baseClock", ComponentDescription.class), new Interval(1));
        simConfig.addMapping(new Attribute("ramSize", ComponentDescription.class), new Interval(6));
        simConfig.addMapping(new Attribute("DDRClass", ComponentDescription.class), new Equal());
        simConfig.addMapping(new Attribute("gpuHashRate", ComponentDescription.class), new Interval(20));
        simConfig.addMapping(new Attribute("gpuSize", ComponentDescription.class), new Interval(4));
        simConfig.addMapping(new Attribute("storageSize", ComponentDescription.class), new Interval(1000));
    }

    public void cycle(CBRQuery query) throws ExecutionException {
        response = new ArrayList<>();
        Collection<RetrievalResult> eval = NNScoringMethod.evaluateSimilarity(_caseBase.getCases(), query, simConfig);
        eval = SelectCases.selectTopKRR(eval, 5);
        System.out.println("Retrieved cases:");
        for (RetrievalResult nse : eval){
            var description = (ComponentDescription)nse.get_case().getDescription();
            response.add(new EvaluationResponseDTO(description.getCpuName(),description.getGpuName(),description.getRamName(),description.getStorageName(), nse.getEval()));
            System.out.println(nse.get_case().getDescription() + " -> " + nse.getEval());
        }
    }

    public void postCycle() throws ExecutionException {

    }

    public CBRCaseBase preCycle() throws ExecutionException {
        _caseBase.init(_connector);
        java.util.Collection<CBRCase> cases = _caseBase.getCases();
        for (CBRCase c: cases)
            System.out.println(c.getDescription());
        return _caseBase;
    }
}
