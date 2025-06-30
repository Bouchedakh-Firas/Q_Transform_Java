package com.example.app.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PerformanceTestResultTest {

    @Test
    public void testConstructorAndGetters() {
        // Create a test instance
        PerformanceTestResult result = new PerformanceTestResult(
                "CPU", 1000L, 1048576L, 4, 1, "Test info");
        
        // Verify getters
        assertEquals("CPU", result.getTestType());
        assertEquals(1000L, result.getExecutionTimeMs());
        assertEquals(1048576L, result.getMemoryUsedBytes());
        assertEquals(4, result.getCpuCount());
        assertEquals(1, result.getThreadCount());
        assertEquals("Test info", result.getAdditionalInfo());
    }
    
    @Test
    public void testSetters() {
        // Create a test instance with default constructor
        PerformanceTestResult result = new PerformanceTestResult();
        
        // Set values
        result.setTestType("Memory");
        result.setExecutionTimeMs(500L);
        result.setMemoryUsedBytes(2097152L);
        result.setCpuCount(8);
        result.setThreadCount(4);
        result.setAdditionalInfo("Updated info");
        
        // Verify values
        assertEquals("Memory", result.getTestType());
        assertEquals(500L, result.getExecutionTimeMs());
        assertEquals(2097152L, result.getMemoryUsedBytes());
        assertEquals(8, result.getCpuCount());
        assertEquals(4, result.getThreadCount());
        assertEquals("Updated info", result.getAdditionalInfo());
    }
    
    @Test
    public void testGetMemoryUsedMB() {
        // Test with 1 MB
        PerformanceTestResult result1 = new PerformanceTestResult();
        result1.setMemoryUsedBytes(1048576L); // 1 MB in bytes
        assertEquals(1.0, result1.getMemoryUsedMB(), 0.001);
        
        // Test with 2.5 MB
        PerformanceTestResult result2 = new PerformanceTestResult();
        result2.setMemoryUsedBytes(2621440L); // 2.5 MB in bytes
        assertEquals(2.5, result2.getMemoryUsedMB(), 0.001);
        
        // Test with 0 bytes
        PerformanceTestResult result3 = new PerformanceTestResult();
        result3.setMemoryUsedBytes(0L);
        assertEquals(0.0, result3.getMemoryUsedMB(), 0.001);
    }
}