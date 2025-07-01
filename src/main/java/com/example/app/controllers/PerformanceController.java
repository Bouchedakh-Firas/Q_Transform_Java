package com.example.app.controllers;

import com.example.app.models.JavaScriptResult;
import com.example.app.models.PerformanceTestResult;
import com.example.app.services.JavaScriptService;
import com.example.app.services.PerformanceTestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * REST controller for handling performance test API requests.
 */
@RestController
@RequestMapping("/api/performance")
public class PerformanceController {

    private static final Logger logger = LoggerFactory.getLogger(PerformanceController.class);
    private final PerformanceTestService performanceTestService;
    private final JavaScriptService javaScriptService;

    @Autowired
    public PerformanceController(PerformanceTestService performanceTestService, JavaScriptService javaScriptService) {
        this.performanceTestService = performanceTestService;
        this.javaScriptService = javaScriptService;
    }

    /**
     * API endpoint for running a CPU performance test.
     * 
     * @return A PerformanceTestResult object with test metrics
     */
    @GetMapping("/cpu")
    public ResponseEntity<PerformanceTestResult> runCpuTest() {
        logger.info("API request received for CPU performance test");
        PerformanceTestResult result = performanceTestService.runCpuTest();
        return ResponseEntity.ok(result);
    }

    /**
     * API endpoint for running a memory performance test.
     * 
     * @return A PerformanceTestResult object with test metrics
     */
    @GetMapping("/memory")
    public ResponseEntity<PerformanceTestResult> runMemoryTest() {
        logger.info("API request received for memory performance test");
        PerformanceTestResult result = performanceTestService.runMemoryTest();
        return ResponseEntity.ok(result);
    }

    /**
     * API endpoint for running a concurrency performance test.
     * 
     * @return A PerformanceTestResult object with test metrics
     */
    @GetMapping("/concurrency")
    public ResponseEntity<PerformanceTestResult> runConcurrencyTest() {
        logger.info("API request received for concurrency performance test");
        PerformanceTestResult result = performanceTestService.runConcurrencyTest();
        return ResponseEntity.ok(result);
    }

    /**
     * API endpoint for getting system information.
     * 
     * @return A map containing system information
     */
    @GetMapping("/system-info")
    public ResponseEntity<Map<String, Object>> getSystemInfo() {
        logger.info("API request received for system information");
        
        Runtime runtime = Runtime.getRuntime();
        Map<String, Object> systemInfo = new HashMap<>();
        
        systemInfo.put("availableProcessors", runtime.availableProcessors());
        systemInfo.put("maxMemory", runtime.maxMemory());
        systemInfo.put("totalMemory", runtime.totalMemory());
        systemInfo.put("freeMemory", runtime.freeMemory());
        systemInfo.put("javaVersion", System.getProperty("java.version"));
        systemInfo.put("javaVendor", System.getProperty("java.vendor"));
        systemInfo.put("osName", System.getProperty("os.name"));
        systemInfo.put("osVersion", System.getProperty("os.version"));
        
        return ResponseEntity.ok(systemInfo);
    }
    
    /**
     * API endpoint for running a JavaScript performance test.
     * This demonstrates using a Java 8 feature (Nashorn) that was removed in Java 17.
     * 
     * @return A PerformanceTestResult object with test metrics
     */
    @GetMapping("/javascript")
    public ResponseEntity<PerformanceTestResult> runJavaScriptTest() {
        logger.info("API request received for JavaScript performance test");
        
        // Get initial memory usage
        Runtime runtime = Runtime.getRuntime();
        long startMemory = runtime.totalMemory() - runtime.freeMemory();
        
        // Start timing
        long startTime = System.currentTimeMillis();
        
        // Run JavaScript performance test
        JavaScriptResult jsResult = javaScriptService.runPerformanceTest();
        
        // End timing
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        
        // Get final memory usage
        long endMemory = runtime.totalMemory() - runtime.freeMemory();
        long memoryUsed = endMemory - startMemory;
        
        // Get CPU count
        int cpuCount = runtime.availableProcessors();
        
        // Create result
        String additionalInfo = "JavaScript engine: Nashorn, ";
        if (jsResult.isSuccess()) {
            additionalInfo += "Test completed successfully, Result: " + jsResult.getResult();
        } else {
            additionalInfo += "Test failed: " + jsResult.getErrorMessage();
        }
        
        PerformanceTestResult result = new PerformanceTestResult(
            "JavaScript",
            executionTime,
            memoryUsed,
            cpuCount,
            1, // Single thread for JavaScript test
            additionalInfo
        );
        
        logger.info("JavaScript test completed in {} ms", executionTime);
        return ResponseEntity.ok(result);
    }
}