package com.example.computercomponents.controller.dto;

public class EvaluationResponseDTO {
    private String cpuName;
    private String gpuName;
    private String ramName;
    private String storageName;
    private double evaluation;

    public EvaluationResponseDTO() {
    }

    public EvaluationResponseDTO(String cpuName, String gpuName, String ramName, String storageName, double evaluation) {
        this.cpuName = cpuName;
        this.gpuName = gpuName;
        this.ramName = ramName;
        this.storageName = storageName;
        this.evaluation = evaluation;
    }

    public String getCpuName() {
        return cpuName;
    }

    public void setCpuName(String cpuName) {
        this.cpuName = cpuName;
    }

    public String getGpuName() {
        return gpuName;
    }

    public void setGpuName(String gpuName) {
        this.gpuName = gpuName;
    }

    public String getRamName() {
        return ramName;
    }

    public void setRamName(String ramName) {
        this.ramName = ramName;
    }

    public String getStorageName() {
        return storageName;
    }

    public void setStorageName(String storageName) {
        this.storageName = storageName;
    }

    public double getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(double evaluation) {
        this.evaluation = evaluation;
    }
}
