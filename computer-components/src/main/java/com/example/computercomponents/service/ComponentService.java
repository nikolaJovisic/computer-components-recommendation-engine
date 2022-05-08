package com.example.computercomponents.service;

import com.example.computercomponents.constants.Prefixes;
import org.apache.jena.query.ParameterizedSparqlString;
import org.apache.jena.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ComponentService {

    private final QueryService queryService;

    @Autowired
    public ComponentService(QueryService queryService) {
        this.queryService = queryService;
    }

    public List<String> getRAMs(String motherboard){
        ParameterizedSparqlString queryStr = new ParameterizedSparqlString();
        queryStr.setNsPrefix("base", Prefixes.BASE_ONTOLOGY_PREFIX);
        queryStr.setNsPrefix("import",Prefixes.IMPORT_ONTOLOGY_PREFIX);
        queryStr.append("SELECT ?s");
        queryStr.append("{");
        queryStr.append("?s import:compatibleRAM base:");
        queryStr.append(motherboard);
        queryStr.append(".");
        queryStr.append("}");
        Query q = queryStr.asQuery();
        var rawResponse =  queryService.executeQuery(q);
        var rams = new ArrayList<String>();
        for(var rawRam : rawResponse){
            rams.add(rawRam.split("#")[1].split(">")[0]);
        }
        return rams;
    }
}
