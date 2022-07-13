package com.example.computercomponents.model;

public class ComponentDescription {
    private String cpuName;
    private String gpuName;
    private String ramName;
    private String storageName;
    private int threadNum;
    private double baseClock;
    private int ramSize;
    private int DDRClass;
    private double gpuHashRate;
    private int gpuSize;
    private int storageSize;


    public ComponentDescription(String cpuName, String gpuName, String ramName, String storageName, int threadNum, double baseClock, int ramSize, int DDRClass, double gpuHashRate, int gpuSize, int storageSize) {
        this.cpuName = cpuName;
        this.gpuName = gpuName;
        this.ramName = ramName;
        this.storageName = storageName;
        this.threadNum = threadNum;
        this.baseClock = baseClock;
        this.ramSize = ramSize;
        this.DDRClass = DDRClass;
        this.gpuHashRate = gpuHashRate;
        this.gpuSize = gpuSize;
        this.storageSize = storageSize;
    }

    @Override
    public String toString() {
        return "ComponentDescription{" +
                "cpuName='" + cpuName + '\'' +
                ", gpuName='" + gpuName + '\'' +
                ", ramName='" + ramName + '\'' +
                ", storageName='" + storageName + '\'' +
                ", threadNum=" + threadNum +
                ", baseClock=" + baseClock +
                ", ramSize=" + ramSize +
                ", DDRClass=" + DDRClass +
                ", gpuHashRate=" + gpuHashRate +
                ", gpuSize=" + gpuSize +
                ", storageSize=" + storageSize +
                '}';
    }

    public ComponentDescription() {
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

    public int getThreadNum() {
        return threadNum;
    }

    public void setThreadNum(int threadNum) {
        this.threadNum = threadNum;
    }

    public double getBaseClock() {
        return baseClock;
    }

    public void setBaseClock(double baseClock) {
        this.baseClock = baseClock;
    }

    public int getRamSize() {
        return ramSize;
    }

    public void setRamSize(int ramSize) {
        this.ramSize = ramSize;
    }

    public int getDDRClass() {
        return DDRClass;
    }

    public void setDDRClass(int DDRClass) {
        this.DDRClass = DDRClass;
    }

    public double getGpuHashRate() {
        return gpuHashRate;
    }

    public void setGpuHashRate(double gpuHashRate) {
        this.gpuHashRate = gpuHashRate;
    }

    public int getGpuSize() {
        return gpuSize;
    }

    public void setGpuSize(int gpuSize) {
        this.gpuSize = gpuSize;
    }

    public int getStorageSize() {
        return storageSize;
    }

    public void setStorageSize(int storageSize) {
        this.storageSize = storageSize;
    }
}
