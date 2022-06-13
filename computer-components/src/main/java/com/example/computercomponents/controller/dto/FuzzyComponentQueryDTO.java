package com.example.computercomponents.controller.dto;

import javax.validation.constraints.NotEmpty;

public class FuzzyComponentQueryDTO {
    @NotEmpty
    private String gpuName;
    @NotEmpty
    private String ramName;
    @NotEmpty
    private String storageName;
    @NotEmpty
    private String cpuName;

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

    public String getCpuName() {
        return cpuName;
    }

    public void setCpuName(String cpuName) {
        this.cpuName = cpuName;
    }
}
