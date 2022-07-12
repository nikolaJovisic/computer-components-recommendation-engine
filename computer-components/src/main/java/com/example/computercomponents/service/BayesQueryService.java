package com.example.computercomponents.service;

import com.example.computercomponents.constants.URL;
import com.example.computercomponents.controller.dto.BayesResponseDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Service;
import unbbayes.io.BaseIO;
import unbbayes.io.NetIO;
import unbbayes.prs.Node;
import unbbayes.prs.bn.JunctionTreeAlgorithm;
import unbbayes.prs.bn.ProbabilisticNetwork;
import unbbayes.prs.bn.ProbabilisticNode;
import unbbayes.util.extension.bn.inference.IInferenceAlgorithm;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;


@Service
public class BayesQueryService {

    // YES STATE
    private final int YES_STATE = 0;

    public List<BayesResponseDTO> createQuery(String symptom) throws IOException, URISyntaxException {

        var net = getProbabilisticNetwork(symptom);
        net.setName(symptom);

        // compile
        compileNetwork(net);

        //set state
        var factNode = (ProbabilisticNode)net.getNode(symptom);
        factNode.addFinding(YES_STATE);

        // propagation
        try {
            net.updateEvidences();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        printNodes(net);
        return getOutputVariables(net);
    }

    private void compileNetwork(ProbabilisticNetwork net) {
        IInferenceAlgorithm algorithm = new JunctionTreeAlgorithm();
        algorithm.setNetwork(net);
        algorithm.run();
    }

    protected ProbabilisticNetwork getProbabilisticNetwork(String symptom) throws URISyntaxException, IOException {
        BaseIO io = new NetIO();
        var path = TypeReference.class.getResource(URL.BAYES_LOGIC_PATH).toURI().getPath() +"/"+ symptom +".net";
        var net = (ProbabilisticNetwork) io.load(new File(path));
        return net;
    }

    private List<BayesResponseDTO> getOutputVariables(ProbabilisticNetwork net) {
        List<Node> nodeList = net.getNodes();
        List<BayesResponseDTO> response = new ArrayList<>();
        for (Node node : nodeList) {
            response.add(new BayesResponseDTO(node.getName(),((ProbabilisticNode)node).getMarginalAt(YES_STATE)));
        }

        return response;
    }

    private void printNodes(ProbabilisticNetwork net){
        System.out.println("\n\n------"+net.getName()+"--------");
        List<Node> nodeList = net.getNodes();
        for (Node node : nodeList) {
            System.out.println("\n"+node.getName());
            for (int i = 0; i < node.getStatesSize(); i++)
                System.out.println(node.getStateAt(i) + ": " + ((ProbabilisticNode)node).getMarginalAt(i));

        }
    }
}
