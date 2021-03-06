package com.example.computercomponents.service;

import com.example.computercomponents.constants.BayesNetworks;
import com.example.computercomponents.controller.dto.BayesResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unbbayes.prs.Node;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Service
public class BayesComponentService {

    private final BayesQueryService bayesQueryService;


    @Autowired

    public BayesComponentService(BayesQueryService bayesQueryService) {
        this.bayesQueryService = bayesQueryService;
    }



    public List<String> getSpecificNodes(String previousSymptom, String criteria) throws URISyntaxException, IOException {
        var retVal = new ArrayList<String>();


        retVal.addAll(getNodesFromNetwork(BayesNetworks.AQUARIUS_NETWORK_NAME,criteria,previousSymptom));
        retVal.addAll(getNodesFromNetwork(BayesNetworks.CASSIOPEIA_NETWORK_NAME,criteria,previousSymptom));
        retVal.addAll(getNodesFromNetwork(BayesNetworks.URSA_MAJOR_NETWORK_NAME,criteria,previousSymptom));

        return retVal;
    }

    public List<BayesResponseDTO> getQueryResponse(List<String> symptoms, List<String> causes) throws Exception {
        var network = bayesQueryService.getNetwork(symptoms.stream().findFirst().get());

        bayesQueryService.compileNetwork(network);

        if(causes!= null)
            symptoms.addAll(causes);
        bayesQueryService.setFactNodes(network,symptoms);

        network.updateEvidences();

        bayesQueryService.printNodes(network);
        return bayesQueryService.getRelevantOutputNodes(network);
    }

    private List<String> getNodesFromNetwork(String networkName,String criteria,String previousSymptom) throws URISyntaxException, IOException {
        var network = bayesQueryService.getProbabilisticNetwork(networkName);

        List<Node> nodeList = network.getNodes();
        List<String> response = new ArrayList<>();

        var sameNetwork = false;

        for (Node node : nodeList){
            if(previousSymptom.equals(node.getName())) sameNetwork = true;
            if(node.getName().contains(criteria)){
                response.add(node.getName());
            }
        }

        if(!previousSymptom.isBlank() && !sameNetwork)
            return new ArrayList<String>();

        return response;
    }
}
