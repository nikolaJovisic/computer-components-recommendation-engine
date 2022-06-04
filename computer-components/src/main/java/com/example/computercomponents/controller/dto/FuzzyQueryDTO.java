package com.example.computercomponents.controller.dto;
import org.jetbrains.annotations.NotNull;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class FuzzyQueryDTO {

    @NotNull
    @Min(0)
    @Max(64)
    private int threadNumber;

    @NotNull
    @Min(0)
    @Max(64)
    private int ramSize;

    @NotNull
    @Min(0)
    @Max(5000)
    private int storageSize;

    @NotNull
    @Min(0)
    @Max(120)
    private int gpuHashRate;

    @NotNull
    @Min(0)
    @Max(32)
    private int gpuSize;

    public int getThreadNumber() {
        return threadNumber;
    }

    public void setThreadNumber(int threadNumber) {
        this.threadNumber = threadNumber;
    }

    public int getRamSize() {
        return ramSize;
    }

    public void setRamSize(int ramSize) {
        this.ramSize = ramSize;
    }

    public int getStorageSize() {
        return storageSize;
    }

    public void setStorageSize(int storageSize) {
        this.storageSize = storageSize;
    }

    public int getGpuSize() {
        return gpuSize;
    }

    public void setGpuSize(int gpuSize) {
        this.gpuSize = gpuSize;
    }

    public int getGpuHashRate() {
        return gpuHashRate;
    }

    public void setGpuHashRate(int gpuHashRate) {
        this.gpuHashRate = gpuHashRate;
    }
}
