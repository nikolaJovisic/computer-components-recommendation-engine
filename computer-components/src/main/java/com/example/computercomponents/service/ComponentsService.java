package com.example.computercomponents.service;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.vocabulary.RDF;
import org.semanticweb.HermiT.Reasoner.ReasonerFactory;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.reasoner.InferenceType;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;

@Service
public class ComponentsService {

    public static void Exec() {
        Model model = ModelFactory.createDefaultModel();
        try {
            InputStream is = new FileInputStream("C:\\Users\\paracelsus\\Desktop\\iz\\rdf\\RDF\\data\\primer.ttl");
            RDFDataMgr.read(model, is, Lang.TURTLE);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        printStatements(model);

        // Dodavanje RDF iskaza
        String subjekatString = "http://ftn.uns.ac.rs/inzenjering-znanja#HoakinFiniks";
        String objekat1String = "http://xmlns.com/foaf/0.1/Person";

        String predikat2String = "http://xmlns.com/foaf/0.1/name";
        String objekat2String = "Hoakin Finiks";

        Resource subjekat = model.createResource(subjekatString);
        Resource objekat1 = model.createResource(objekat1String);
        subjekat.addProperty(RDF.type, objekat1);

        Property predikat2 = model.createProperty(predikat2String);
        subjekat.addProperty(predikat2, objekat2String, XSDDatatype.XSDstring);

        printStatements(model);
    }

    private static void printStatements(Model model) {
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
    }

    public static void owl(String[] args)
            throws OWLOntologyCreationException, OWLOntologyStorageException, FileNotFoundException {
        // uvoz ontologije iz fajl sistema

        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();

        File file = new File(
                "/home/milica/Documents/nastava/inzenjering znanja/2021-2022/vezba 03 - owl 2 dl i owl api/pizza.owl");
        OWLOntology o = manager.loadOntologyFromOntologyDocument(file);
        // System.out.println(o);

        /*
         * //uvoz ontologije sa Interneta pomocu IRI-a OWLOntologyManager man =
         * OWLManager.createOWLOntologyManager(); IRI pizzaontology =
         * IRI.create("http://protege.stanford.edu/ontologies/pizza/pizza.owl");
         * OWLOntology o = man.loadOntology(pizzaontology); System.out.println(o);
         */

        /*
         * // cuvanje ontologije u datoteci fajl sistema File fileout = new
         * File("/home/milica/Desktop/pizza.owl"); IRI pizzaontology =
         * IRI.create("http://protege.stanford.edu/ontologies/pizza/pizza.owl");
         * OWLOntology o = manager.loadOntology(pizzaontology); manager.saveOntology(o,
         * new FunctionalSyntaxDocumentFormat(), new FileOutputStream(fileout));
         */

        // kreiranje nove ontologije
        IRI IOR = IRI.create("http://owl.api.tutorial");
        OWLOntologyManager man = OWLManager.createOWLOntologyManager();
        OWLOntology o1 = man.createOntology(IOR);
        OWLDataFactory df = o.getOWLOntologyManager().getOWLDataFactory();
        OWLClass person = df.getOWLClass(IOR + "#Person");
        OWLDeclarationAxiom da = df.getOWLDeclarationAxiom(person);
        o1.add(da);
        // System.out.println(o1);

        // kreiranje potklase
        OWLClass woman = df.getOWLClass(IOR + "#Woman");
        OWLSubClassOfAxiom w_sub_p = df.getOWLSubClassOfAxiom(woman, person);
        o1.add(w_sub_p);
        // System.out.println(o1);

        // kreiranje kompleksne klase
        OWLClass A = df.getOWLClass(IOR + "#A");
        OWLClass B = df.getOWLClass(IOR + "#B");
        OWLClass X = df.getOWLClass(IOR + "#X");
        OWLObjectProperty R = df.getOWLObjectProperty(IOR + "#R");
        OWLObjectProperty S = df.getOWLObjectProperty(IOR + "#S");
        OWLSubClassOfAxiom ax = df.getOWLSubClassOfAxiom(df.getOWLObjectSomeValuesFrom(R, A),
                df.getOWLObjectSomeValuesFrom(S, B));
        o1.add(ax);
        System.out.println(o1);

        // dodavanje i brisanje pogresne klase
        OWLClass mann = df.getOWLClass(IOR + "#Man");
        // mann with two "n" to avoid confusion with the OWLOntologyManager
        OWLClass woman1 = df.getOWLClass(IOR + "#Woman");
        OWLSubClassOfAxiom m_sub_w = df.getOWLSubClassOfAxiom(mann, woman1);
        o1.add(m_sub_w);
        o1.remove(m_sub_w);

        // koriscenje rezonera
        OWLReasonerFactory rf = new ReasonerFactory();
        OWLReasoner r = rf.createReasoner(o);
        r.precomputeInferences(InferenceType.CLASS_HIERARCHY);

        //dobavljanje svih potklasa nastalih zakljucivanjem
        OWLOntology o2;
        OWLDataFactory df2 = man.getOWLDataFactory();
        IRI pizzaontology = IRI.create("http://protege.stanford.edu/ontologies/pizza/pizza.owl");
        try {
            o = man.loadOntology(pizzaontology);
            OWLReasonerFactory rf2 = new ReasonerFactory();
            OWLReasoner r2 = rf2.createReasoner(o);
            r2.precomputeInferences(InferenceType.CLASS_HIERARCHY);
            r2.getSubClasses(df2.getOWLClass("http://www.co−ode.org/ontologies/pizza/pizza.owl#RealItalianPizza"), false)
            //.forEach(System.out::println);
            ;
        } catch (OWLOntologyCreationException e) {
            e.printStackTrace();
        }
    }
}
