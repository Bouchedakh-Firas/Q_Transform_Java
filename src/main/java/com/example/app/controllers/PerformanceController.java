package com.example.app.controllers;

import com.example.app.models.PerformanceTestResult;
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

    @Autowired
    public PerformanceController(PerformanceTestService performanceTestService) {
        this.performanceTestService = performanceTestService;
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
}