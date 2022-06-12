package com.example.computercomponents.controller.dto;

public class BayesResponseDTO {
    private String outputVariable;
    private double percentage;

    public BayesResponseDTO(String outputVariable, double percentage) {
        this.outputVariable = outputVariable;
        this.percentage = percentage;
    }

    public String getOutputVariable() {
        return outputVariable;
    }

    public void setOutputVariable(String outputVariable) {
        this.outputVariable = outputVariable;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }
}
