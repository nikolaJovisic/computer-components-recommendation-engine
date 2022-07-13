package connector;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Collection;
import java.util.LinkedList;

import model.ConfigurationCaseDescription;
import ucm.gaia.jcolibri.cbrcore.CBRCase;
import ucm.gaia.jcolibri.cbrcore.CaseBaseFilter;
import ucm.gaia.jcolibri.cbrcore.Connector;
import ucm.gaia.jcolibri.exception.InitializingException;
import ucm.gaia.jcolibri.util.FileIO;

public class ConfigurationCsvConnector implements Connector {
	
	@Override
	public Collection<CBRCase> retrieveAllCases() {
		LinkedList<CBRCase> cases = new LinkedList<CBRCase>();
		
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(FileIO.openFile("data/example.csv")));
			if (br == null)
				throw new Exception("Error opening file");

			String line = "";
			while ((line = br.readLine()) != null) {
				if (line.startsWith("#") || (line.length() == 0))
					continue;
				String[] values = line.split(";");

				CBRCase cbrCase = new CBRCase();

				ConfigurationCaseDescription configurationCaseDescription = new ConfigurationCaseDescription();
				
				
				// TODO
				configurationCaseDescription.setBrand(values[0]);
				configurationCaseDescription.setNameProcessor(values[1]);
				configurationCaseDescription.setBaseClockProcessor(Double.parseDouble(values[2]));
				configurationCaseDescription.setTypeRam(values[3]);
				configurationCaseDescription.setSizeRam(Integer.parseInt(values[4]));
				configurationCaseDescription.setTypeStorage(values[5]);
				configurationCaseDescription.setSizeStorage(Integer.parseInt(values[6]));
				configurationCaseDescription.setNameGpu(values[7]);
				configurationCaseDescription.setSizeGpu(Integer.parseInt(values[8]));
				configurationCaseDescription.setCoresGpu(Integer.parseInt(values[9]));
				configurationCaseDescription.setConnector(values[10]);
				
				cbrCase.setDescription(configurationCaseDescription);
				cases.add(cbrCase);
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cases;
	}

	@Override
	public Collection<CBRCase> retrieveSomeCases(CaseBaseFilter arg0) {
		return null;
	}

	@Override
	public void storeCases(Collection<CBRCase> arg0) {
	}
	
	@Override
	public void close() {
	}

	@Override
	public void deleteCases(Collection<CBRCase> arg0) {
	}

	@Override
	public void initFromXMLfile(URL arg0) throws InitializingException {
	}

}