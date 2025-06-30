package com.example.app.models;

/**
 * Model class for performance test results.
 */
public class PerformanceTestResult {
    private String testType;
    private long executionTimeMs;
    private long memoryUsedBytes;
    private int cpuCount;
    private int threadCount;
    private String additionalInfo;

    public PerformanceTestResult() {
        // Default constructor
    }

    public PerformanceTestResult(String testType, long executionTimeMs, long memoryUsedBytes, int cpuCount, int threadCount, String additionalInfo) {
        this.testType = testType;
        this.executionTimeMs = executionTimeMs;
        this.memoryUsedBytes = memoryUsedBytes;
        this.cpuCount = cpuCount;
        this.threadCount = threadCount;
        this.additionalInfo = additionalInfo;
    }

    public String getTestType() {
        return testType;
    }

    public void setTestType(String testType) {
        this.testType = testType;
    }

    public long getExecutionTimeMs() {
        return executionTimeMs;
    }

    public void setExecutionTimeMs(long executionTimeMs) {
        this.executionTimeMs = executionTimeMs;
    }

    public long getMemoryUsedBytes() {
        return memoryUsedBytes;
    }

    public void setMemoryUsedBytes(long memoryUsedBytes) {
        this.memoryUsedBytes = memoryUsedBytes;
    }

    public int getCpuCount() {
        return cpuCount;
    }

    public void setCpuCount(int cpuCount) {
        this.cpuCount = cpuCount;
    }

    public int getThreadCount() {
        return threadCount;
    }

    public void setThreadCount(int threadCount) {
        this.threadCount = threadCount;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }
    
    // Helper method to get memory in MB for display
    public double getMemoryUsedMB() {
        return memoryUsedBytes / (1024.0 * 1024.0);
    }
}