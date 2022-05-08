package com.example.computercomponents.service;

import com.example.computercomponents.constants.URL;
import com.fasterxml.jackson.core.type.TypeReference;

import org.apache.jena.query.*;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.springframework.stereotype.Service;


@Service
public  class QueryService {

    protected Model model;

    public QueryService() {
        model = ModelFactory.createDefaultModel();
        try{
            InputStream is = TypeReference.class.getResourceAsStream(URL.CLASSES_AND_INSTANCES_PATH);
            RDFDataMgr.read(model,is,Lang.TURTLE);
            is.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("Model successfully loaded!");
    }

    public List<String> executeQuery(String queryString)  {
        System.out.println("################ QUERY #######################");
        System.out.println(queryString);
        Query query = QueryFactory.create(queryString);
        QueryExecution exec = QueryExecutionFactory.create(query,model);
        return PrintQuery(exec.execSelect());
    }

    public List<String> executeQuery(Query query){
        System.out.println("################ QUERY #######################");
        System.out.println(query.toString());
        QueryExecution exec = QueryExecutionFactory.create(query,model);
        return PrintQuery(exec.execSelect());
    }

    public List<String> PrintQuery(ResultSet resultSet){
        System.out.println("################ QUERY RESULT #######################");
        List<String> strings = new ArrayList<>();
        while (resultSet.hasNext()) {
            QuerySolution solution = resultSet.nextSolution();
            strings.add(solution.toString());
            System.out.println(solution);
        }
        return strings;
    }

    public  void printModel() {
        System.out.println("################ MODEL #######################");
        StmtIterator iter = model.listStatements();
        while (iter.hasNext()) {
            Statement stmt = iter.nextStatement();
            Resource subject = stmt.getSubject();
            Property predicate = stmt.getPredicate();
            RDFNode object = stmt.getObject();

            System.out.print(subject.toString() + " ");
            System.out.print(predicate.toString() + " ");
            if (object instanceof Resource) {
                System.out.print(object.toString());
            } else { // object is a literal
                System.out.print(" \"" + object.toString() + "\"");
            }
            System.out.println(" .");
        }
        System.out.println("\n\n");
    }

}
