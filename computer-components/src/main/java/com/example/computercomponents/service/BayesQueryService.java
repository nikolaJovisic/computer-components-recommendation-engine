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
    private final int stateIndex = 0;

    public List<BayesResponseDTO> createQuery(String symptom) throws IOException, URISyntaxException {
        BaseIO io = new NetIO();
        var path = TypeReference.class.getResource(URL.BAYES_LOGIC_PATH).toURI().getPath() +"/"+ symptom +".net";
        var net = (ProbabilisticNetwork) io.load(new File(path));

        // compiling
        IInferenceAlgorithm algorithm = new JunctionTreeAlgorithm();
        algorithm.setNetwork(net);
        algorithm.run();
        
        //set state
        ProbabilisticNode factNode = (ProbabilisticNode)net.getNode(symptom);
        factNode.addFinding(stateIndex);

        // propagation
        try {
            net.updateEvidences();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // states overview after propagation
        List<Node> nodeList = net.getNodes();
        List<BayesResponseDTO> response = new ArrayList<>();
        for (Node node : nodeList) {
            response.add(new BayesResponseDTO(node.getName(),((ProbabilisticNode)node).getMarginalAt(stateIndex)));
        }

        return response;
    }
}
