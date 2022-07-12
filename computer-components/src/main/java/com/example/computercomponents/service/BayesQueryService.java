package com.example.computercomponents.service;

import com.example.computercomponents.constants.BayesNetworks;
import com.example.computercomponents.constants.NodeTypes;
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
    private final float MAX_VALUE = 0.99999f;


    protected void setFactNodes(ProbabilisticNetwork net,List<String> nodeNames){
        for(var nodeName: nodeNames){
            if(nodeName.isBlank()) continue;
            var factNode = (ProbabilisticNode)net.getNode(nodeName);
            factNode.addFinding(YES_STATE);
        }
    }

    protected void compileNetwork(ProbabilisticNetwork net) {
        IInferenceAlgorithm algorithm = new JunctionTreeAlgorithm();
        algorithm.setNetwork(net);
        algorithm.run();
    }

    protected ProbabilisticNetwork getProbabilisticNetwork(String file) throws URISyntaxException, IOException {
        BaseIO io = new NetIO();
        var path = TypeReference.class.getResource(URL.BAYES_LOGIC_PATH).toURI().getPath() +"/"+ file +".net";
        return (ProbabilisticNetwork) io.load(new File(path));
    }

    protected  ProbabilisticNetwork getNetwork(String symptom) throws IOException, URISyntaxException, IllegalAccessException {
        BaseIO io = new NetIO();
        var fileNames = BayesNetworks.class.getFields();
        for(var field : fileNames){
            var path = TypeReference.class.getResource(URL.BAYES_LOGIC_PATH).toURI().getPath() +"/"+ field.get(null).toString() +".net";
            var net = (ProbabilisticNetwork) io.load(new File(path));
            if(checkIfNodeExists(net,symptom))
                return net;
        }

        return null;
    }

    private boolean checkIfNodeExists(ProbabilisticNetwork network, String symptom){
        List<Node> nodeList = network.getNodes();

        for (Node node : nodeList)
            if(node.getName().equals(symptom))
                return true;

        return false;
    }

    protected List<BayesResponseDTO> getRelevantOutputNodes(ProbabilisticNetwork network){
        List<Node> nodeList = network.getNodes();
        List<BayesResponseDTO> response = new ArrayList<>();
        for (Node node : nodeList) {
            var value = ((ProbabilisticNode)node).getMarginalAt(YES_STATE);
            if(value>=MAX_VALUE && node.getName().contains(NodeTypes.SYMPTOM_SUFFIX)){
                var relevantNodes = node.getAdjacentNodes();
                for(var relevantNode : relevantNodes)
                    response.add(new BayesResponseDTO(relevantNode.getName(),((ProbabilisticNode)relevantNode).getMarginalAt(YES_STATE)));
            }

        }
        return response;
    }

    protected void printNodes(ProbabilisticNetwork net){
        System.out.println("\n\n------"+net.getName()+"--------");
        List<Node> nodeList = net.getNodes();
        for (Node node : nodeList) {
            System.out.println("\n"+node.getName());
            for (int i = 0; i < node.getStatesSize(); i++)
                System.out.println(node.getStateAt(i) + ": " + ((ProbabilisticNode)node).getMarginalAt(i));

        }
    }

}
