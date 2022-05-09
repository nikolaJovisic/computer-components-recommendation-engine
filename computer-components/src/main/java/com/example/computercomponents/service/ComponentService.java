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

    public List<String> getMotherboardCompatibleComponents(String componentName,String motherboard){
        ParameterizedSparqlString queryStr = new ParameterizedSparqlString();
        queryStr.setNsPrefix("base", Prefixes.BASE_ONTOLOGY_PREFIX);
        queryStr.setNsPrefix("import",Prefixes.IMPORT_ONTOLOGY_PREFIX);
        queryStr.append("SELECT ?s");
        queryStr.append("{");
        queryStr.append("?s import:compatible");
        queryStr.append(componentName);
        queryStr.append(" base:");
        queryStr.append(motherboard);
        queryStr.append(".");
        queryStr.append("}");
        return getQueryResult(queryStr);
    }

    public List<String> getComponents(String component){
        ParameterizedSparqlString queryStr = new ParameterizedSparqlString();
        queryStr.setNsPrefix("rdf", Prefixes.RDF);
        queryStr.setNsPrefix("import",Prefixes.IMPORT_ONTOLOGY_PREFIX);
        queryStr.append("SELECT ?s");
        queryStr.append("{");
        queryStr.append("?s rdf:type");
        queryStr.append(" import:");
        queryStr.append(component);
        queryStr.append(".");
        queryStr.append("}");
        return getQueryResult(queryStr);
    }

    public List<String> getBetterComponents(String componentName,String dataProperty){
        ParameterizedSparqlString queryStr = new ParameterizedSparqlString();
        queryStr.setNsPrefix("base", Prefixes.BASE_ONTOLOGY_PREFIX);
        queryStr.setNsPrefix("import",Prefixes.IMPORT_ONTOLOGY_PREFIX);
        queryStr.setNsPrefix("rdf", Prefixes.RDF);
        queryStr.setNsPrefix("owl", Prefixes.OWL);
        queryStr.append("SELECT ?s");
        queryStr.append("{");
        queryStr.append(" base:");
        queryStr.append(componentName);
        queryStr.append(" import:");
        queryStr.append(dataProperty);
        queryStr.append(" ?o .");
        queryStr.append(" base:");
        queryStr.append(componentName);
        queryStr.append(" rdf:type ?t .");
        queryStr.append(" ?s rdf:type ?t .");
        queryStr.append("?s import:");
        queryStr.append(dataProperty);
        queryStr.append(" ?x .");
        queryStr.append(" FILTER(?x>?o && ?t!=owl:NamedIndividual)");
        queryStr.append("}");
        return getQueryResult(queryStr);

    }


    private ArrayList<String> getQueryResult(ParameterizedSparqlString queryStr) {
        Query q = queryStr.asQuery();
        var rawResponse =  queryService.executeQuery(q);
        var components = new ArrayList<String>();
        for(var rawRam : rawResponse){
            components.add(rawRam.split("#")[1].split(">")[0]);
        }
        return components;
    }
}
