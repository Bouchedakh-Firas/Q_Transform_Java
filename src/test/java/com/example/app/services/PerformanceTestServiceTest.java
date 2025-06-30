package com.example.app.services;

import com.example.app.models.PerformanceTestResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PerformanceTestServiceTest {

    @Autowired
    private PerformanceTestService performanceTestService;

    @Test
    public void testRunCpuTest() {
        PerformanceTestResult result = performanceTestService.runCpuTest();
        
        assertNotNull(result);
        assertEquals("CPU", result.getTestType());
        assertTrue(result.getExecutionTimeMs() > 0);
        assertTrue(result.getMemoryUsedBytes() >= 0);
        assertTrue(result.getCpuCount() > 0);
        assertEquals(1, result.getThreadCount());
        assertNotNull(result.getAdditionalInfo());
        assertTrue(result.getAdditionalInfo().contains("prime numbers"));
    }

    @Test
    public void testRunMemoryTest() {
        PerformanceTestResult result = performanceTestService.runMemoryTest();
        
        assertNotNull(result);
        assertEquals("Memory", result.getTestType());
        assertTrue(result.getExecutionTimeMs() > 0);
        assertTrue(result.getMemoryUsedBytes() > 0);
        assertTrue(result.getCpuCount() > 0);
        assertEquals(1, result.getThreadCount());
        assertNotNull(result.getAdditionalInfo());
        assertTrue(result.getAdditionalInfo().contains("array"));
    }

    @Test
    public void testRunConcurrencyTest() {
        PerformanceTestResult result = performanceTestService.runConcurrencyTest();
        
        assertNotNull(result);
        assertEquals("Concurrency", result.getTestType());
        assertTrue(result.getExecutionTimeMs() > 0);
        assertTrue(result.getMemoryUsedBytes() >= 0);
        assertTrue(result.getCpuCount() > 0);
        assertTrue(result.getThreadCount() > 0);
        assertNotNull(result.getAdditionalInfo());
        assertTrue(result.getAdditionalInfo().contains("threads"));
    }
    
    @Test
    public void testAllTestsCompleteQuickly() {
        long startTime = System.currentTimeMillis();
        
        performanceTestService.runCpuTest();
        performanceTestService.runMemoryTest();
        performanceTestService.runConcurrencyTest();
        
        long totalTime = System.currentTimeMillis() - startTime;
        
        // All tests combined should complete in less than 45 seconds (15 seconds per test)
        assertTrue(totalTime < 45000, "All tests should complete in less than 45 seconds");
    }
}