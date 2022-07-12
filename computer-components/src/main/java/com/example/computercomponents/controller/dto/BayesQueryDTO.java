package com.example.computercomponents.controller.dto;

import javax.validation.constraints.NotEmpty;
import java.util.List;

public class BayesQueryDTO {

    @NotEmpty
    private List<String> symptoms;
    private List<String> causes;

    public BayesQueryDTO() {
    }

    public List<String> getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(List<String> symptoms) {
        this.symptoms = symptoms;
    }

    public List<String> getCauses() {
        return causes;
    }

    public void setCauses(List<String> causes) {
        this.causes = causes;
    }
}
