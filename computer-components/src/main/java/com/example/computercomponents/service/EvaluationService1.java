package com.example.computercomponents.service;

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

import com.example.computercomponents.service.EvaluationService;
import com.example.computercomponents.model.ComponentDescription;
import ucm.gaia.jcolibri.casebase.LinealCaseBase;
import ucm.gaia.jcolibri.cbraplications.StandardCBRApplication;
import ucm.gaia.jcolibri.cbrcore.Attribute;
import ucm.gaia.jcolibri.cbrcore.CBRCase;
import ucm.gaia.jcolibri.cbrcore.CBRCaseBase;
import ucm.gaia.jcolibri.cbrcore.CBRQuery;
import ucm.gaia.jcolibri.cbrcore.CaseBaseFilter;
import ucm.gaia.jcolibri.cbrcore.CaseComponent;
import ucm.gaia.jcolibri.cbrcore.Connector;
import ucm.gaia.jcolibri.connector.PlainTextConnector;
import ucm.gaia.jcolibri.exception.ExecutionException;
import ucm.gaia.jcolibri.exception.InitializingException;
import ucm.gaia.jcolibri.extensions.recommendation.casesDisplay.UserChoice;
import ucm.gaia.jcolibri.extensions.recommendation.conditionals.BuyOrQuit;
import ucm.gaia.jcolibri.extensions.recommendation.conditionals.ContinueOrFinish;
import ucm.gaia.jcolibri.extensions.recommendation.navigationByProposing.CriticalUserChoice;
import ucm.gaia.jcolibri.extensions.recommendation.navigationByProposing.CritiqueOption;
import ucm.gaia.jcolibri.extensions.recommendation.navigationByProposing.DisplayCasesTableWithCritiquesMethod;
import ucm.gaia.jcolibri.extensions.recommendation.navigationByProposing.queryElicitation.MoreLikeThis;
import ucm.gaia.jcolibri.method.gui.formFilling.ObtainQueryWithFormMethod;
import ucm.gaia.jcolibri.method.retrieve.RetrievalResult;
import ucm.gaia.jcolibri.method.retrieve.FilterBasedRetrieval.FilterBasedRetrievalMethod;
import ucm.gaia.jcolibri.method.retrieve.FilterBasedRetrieval.FilterConfig;
import ucm.gaia.jcolibri.method.retrieve.FilterBasedRetrieval.predicates.NotEqual;
import ucm.gaia.jcolibri.method.retrieve.FilterBasedRetrieval.predicates.QueryLess;
import ucm.gaia.jcolibri.method.retrieve.FilterBasedRetrieval.predicates.QueryMore;
import ucm.gaia.jcolibri.method.retrieve.NNretrieval.NNConfig;
import ucm.gaia.jcolibri.method.retrieve.NNretrieval.NNScoringMethod;
import ucm.gaia.jcolibri.method.retrieve.NNretrieval.similarity.global.Average;
import ucm.gaia.jcolibri.method.retrieve.NNretrieval.similarity.local.Equal;
import ucm.gaia.jcolibri.method.retrieve.NNretrieval.similarity.local.Interval;
import ucm.gaia.jcolibri.method.retrieve.NNretrieval.similarity.local.Table;
import ucm.gaia.jcolibri.method.retrieve.NNretrieval.similarity.local.Threshold;
import ucm.gaia.jcolibri.method.retrieve.NNretrieval.similarity.local.recommenders.InrecaLessIsBetter;
import ucm.gaia.jcolibri.method.retrieve.NNretrieval.similarity.local.recommenders.InrecaMoreIsBetter;
import ucm.gaia.jcolibri.method.retrieve.selection.SelectCases;
import ucm.gaia.jcolibri.util.FileIO;

public class EvaluationService1 implements StandardCBRApplication {
    Connector _connector;
    CBRCaseBase _caseBase;

    NNConfig simConfig;

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
        Collection<RetrievalResult> eval = NNScoringMethod.evaluateSimilarity(_caseBase.getCases(), query, simConfig);
        eval = SelectCases.selectTopKRR(eval, 5);
        System.out.println("Retrieved cases:");
        for (RetrievalResult nse : eval)
            System.out.println(nse.get_case().getDescription() + " -> " + nse.getEval());
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
