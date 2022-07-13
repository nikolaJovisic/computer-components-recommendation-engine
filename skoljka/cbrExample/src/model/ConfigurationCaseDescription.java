package model;

import ucm.gaia.jcolibri.cbrcore.Attribute;
import ucm.gaia.jcolibri.cbrcore.CaseComponent;

public class ConfigurationCaseDescription implements CaseComponent {
	
	private String brand;
	private String nameProcessor;
	private double baseClockProcessor;
	private String typeRam;
	private int sizeRam;
	private String typeStorage;
	private int sizeStorage;
	private String nameGpu;
	private int sizeGpu;
	private int coresGpu;
	private String connector;
	
	

	public String getBrand() {
		return brand;
	}



	public void setBrand(String brand) {
		this.brand = brand;
	}



	public String getNameProcessor() {
		return nameProcessor;
	}



	public void setNameProcessor(String nameProcessor) {
		this.nameProcessor = nameProcessor;
	}



	public double getBaseClockProcessor() {
		return baseClockProcessor;
	}



	public void setBaseClockProcessor(double baseClockProcessor) {
		this.baseClockProcessor = baseClockProcessor;
	}



	public String getTypeRam() {
		return typeRam;
	}



	public void setTypeRam(String typeRam) {
		this.typeRam = typeRam;
	}



	public int getSizeRam() {
		return sizeRam;
	}



	public void setSizeRam(int sizeRam) {
		this.sizeRam = sizeRam;
	}



	public String getTypeStorage() {
		return typeStorage;
	}



	public void setTypeStorage(String typeStorage) {
		this.typeStorage = typeStorage;
	}



	public int getSizeStorage() {
		return sizeStorage;
	}



	public void setSizeStorage(int sizeStorage) {
		this.sizeStorage = sizeStorage;
	}



	public String getNameGpu() {
		return nameGpu;
	}



	public void setNameGpu(String nameGpu) {
		this.nameGpu = nameGpu;
	}



	public int getSizeGpu() {
		return sizeGpu;
	}



	public void setSizeGpu(int sizeGpu) {
		this.sizeGpu = sizeGpu;
	}



	public int getCoresGpu() {
		return coresGpu;
	}



	public void setCoresGpu(int coresGpu) {
		this.coresGpu = coresGpu;
	}



	public String getConnector() {
		return connector;
	}



	public void setConnector(String connector) {
		this.connector = connector;
	}

	

	@Override
	public String toString() {
		return "ConfigurationCaseDescription [brand=" + brand + ", nameProcessor=" + nameProcessor
				+ ", baseClockProcessor=" + baseClockProcessor + ", typeRam=" + typeRam + ", sizeRam=" + sizeRam
				+ ", typeStorage=" + typeStorage + ", sizeStorage=" + sizeStorage + ", nameGpu=" + nameGpu
				+ ", sizeGpu=" + sizeGpu + ", coresGpu=" + coresGpu + ", connector=" + connector + "]";
	}



	@Override
	public Attribute getIdAttribute() {
		return new Attribute("id",this.getClass());
	}
}
