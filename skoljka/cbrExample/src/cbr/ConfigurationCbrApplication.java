package cbr;

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

import connector.ConfigurationCsvConnector;
import model.ConfigurationCaseDescription;
import similarity.TableSimilarity;
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

public class ConfigurationCbrApplication implements StandardCBRApplication {
	
	Connector _connector;  /** Connector object */
	CBRCaseBase _caseBase;  /** CaseBase object */

	NNConfig simConfig;  /** KNN configuration */
	
	public void configure() throws ExecutionException {
		_connector =  new ConfigurationCsvConnector();
		
		_caseBase = new LinealCaseBase();  // Create a Lineal case base for in-memory organization
		
		simConfig = new NNConfig(); // KNN configuration
		simConfig.setDescriptionSimFunction(new Average());  // global similarity function = average
		
		// simConfig.addMapping(new Attribute("attribute", CaseDescription.class), new Interval(5));
		// TODO
		simConfig.addMapping(new Attribute("brand", ConfigurationCaseDescription.class), new Equal());
		simConfig.addMapping(new Attribute("baseClockProcessor", ConfigurationCaseDescription.class), new Interval(1));
		simConfig.addMapping(new Attribute("typeRam", ConfigurationCaseDescription.class), new Equal());
		simConfig.addMapping(new Attribute("sizeRam", ConfigurationCaseDescription.class), new Interval(6));
		simConfig.addMapping(new Attribute("typeStorage", ConfigurationCaseDescription.class), new Equal());
		simConfig.addMapping(new Attribute("sizeStorage", ConfigurationCaseDescription.class), new Interval(1000));
		simConfig.addMapping(new Attribute("sizeGpu", ConfigurationCaseDescription.class), new Interval(4));
		simConfig.addMapping(new Attribute("coresGpu", ConfigurationCaseDescription.class), new Interval(1000));
		simConfig.addMapping(new Attribute("connector", ConfigurationCaseDescription.class), new Equal());

		// Equal - returns 1 if both individuals are equal, otherwise returns 0
		// Interval - returns the similarity of two number inside an interval: sim(x,y) = 1-(|x-y|/interval)
		// Threshold - returns 1 if the difference between two numbers is less than a threshold, 0 in the other case
		// EqualsStringIgnoreCase - returns 1 if both String are the same despite case letters, 0 in the other case
		// MaxString - returns a similarity value depending of the biggest substring that belong to both strings
		// EnumDistance - returns the similarity of two enum values as the their distance: sim(x,y) = |ord(x) - ord(y)|
		// EnumCyclicDistance - computes the similarity between two enum values as their cyclic distance
		// Table - uses a table to obtain the similarity between two values. Allowed values are Strings or Enums. The table is read from a text file.
		// TableSimilarity(List<String> values).setSimilarity(value1,value2,similarity) 
	}

	public void cycle(CBRQuery query) throws ExecutionException {
		Collection<RetrievalResult> eval = NNScoringMethod.evaluateSimilarity(_caseBase.getCases(), query, simConfig);
		eval = SelectCases.selectTopKRR(eval, 3);
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

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int numOfStrings = 4;
		String[] string = new String[4];
		StandardCBRApplication recommender = new ConfigurationCbrApplication();
		
		for(int i = 0;i < string.length;i++) {
			if(i == 0) {
				System.out.println("Type in brand of processor: ");
			} else if(i == 1) {
				System.out.println("Type in type of RAM: ");
			} else if(i == 2) {
				System.out.println("Type in type of storage: ");
			} else {
				System.out.println("Type in connector: ");
			}
			string[i] = sc.nextLine();
		}
		
		
		String brand = string[0];
		System.out.println("Type in base clock of processor(GHz): ");
		Double baseClock = sc.nextDouble();
		String typeRAM = string[1];
		System.out.println("Type in size of RAM: ");
		Integer sizeRAM = sc.nextInt();
		String typeStorage = string[2];
		System.out.println("Type in size of storage(GB): ");
		int sizeStorage = sc.nextInt();
		System.out.println("Type in memory of GPU(GB): ");
		int sizeGPU = sc.nextInt();
		System.out.println("Type in number of cores of GPU: ");
		int coresGPU = sc.nextInt();
		String connector = string[3];
		
		try {
			recommender.configure();

			recommender.preCycle();

			CBRQuery query = new CBRQuery();
			ConfigurationCaseDescription configurationCaseDescription = new ConfigurationCaseDescription();
			
			// TODO
			configurationCaseDescription.setBrand(brand);
			configurationCaseDescription.setBaseClockProcessor(baseClock);
			configurationCaseDescription.setTypeRam(typeRAM);
			configurationCaseDescription.setSizeRam(sizeRAM);
			configurationCaseDescription.setTypeStorage(typeStorage);
			configurationCaseDescription.setSizeStorage(sizeStorage);
			configurationCaseDescription.setSizeGpu(sizeGPU);
			configurationCaseDescription.setCoresGpu(coresGPU);
			configurationCaseDescription.setConnector(connector);
			
			
			query.setDescription( configurationCaseDescription );

			recommender.cycle(query);

			recommender.postCycle();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}