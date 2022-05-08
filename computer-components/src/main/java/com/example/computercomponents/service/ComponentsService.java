package com.example.computercomponents.service;

import com.example.computercomponents.constants.URL;
import com.fasterxml.jackson.core.type.TypeReference;

import org.apache.jena.query.*;

import org.springframework.stereotype.Service;


import java.io.InputStream;


import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;


@Service
public class ComponentsService {

    public void LoadOntology(String queryString)  {
        Model model = ModelFactory.createDefaultModel();
        try{
            InputStream is = TypeReference.class.getResourceAsStream(URL.CLASSES_AND_INSTANCES_PATH);
            RDFDataMgr.read(model,is,Lang.TURTLE);
            is.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        printStatements(model);
        System.out.println(queryString);
        Query query = QueryFactory.create(queryString);
        QueryExecution exec = QueryExecutionFactory.create(query,model);
        PrintQuery(exec.execSelect());

    }

    public void PrintQuery(ResultSet resultSet){
        System.out.println("################ QUERY RESULT #######################");
        while (resultSet.hasNext()) {
            QuerySolution solution = resultSet.nextSolution();
            System.out.println(solution);
        }
    }

    private static void printStatements(Model model) {
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
