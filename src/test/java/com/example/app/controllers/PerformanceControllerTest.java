package com.example.app.controllers;

import com.example.app.models.PerformanceTestResult;
import com.example.app.services.JavaScriptService;
import com.example.app.services.PerformanceTestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PerformanceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PerformanceTestService performanceTestService;
    
    @MockBean
    private JavaScriptService javaScriptService;

    @Test
    public void testRunCpuTest() throws Exception {
        // Prepare mock result
        PerformanceTestResult mockResult = new PerformanceTestResult(
                "CPU", 1000L, 1048576L, 4, 1, "Found 78498 prime numbers up to 1000000");

        when(performanceTestService.runCpuTest()).thenReturn(mockResult);

        // Perform request and verify response
        mockMvc.perform(get("/api/performance/cpu"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.testType", is("CPU")))
                .andExpect(jsonPath("$.executionTimeMs", is(1000)))
                .andExpect(jsonPath("$.memoryUsedBytes", is(1048576)))
                .andExpect(jsonPath("$.cpuCount", is(4)))
                .andExpect(jsonPath("$.threadCount", is(1)))
                .andExpect(jsonPath("$.additionalInfo", containsString("prime numbers")));

        verify(performanceTestService, times(1)).runCpuTest();
    }

    @Test
    public void testRunMemoryTest() throws Exception {
        // Prepare mock result
        PerformanceTestResult mockResult = new PerformanceTestResult(
                "Memory", 500L, 41943040L, 4, 1, "Allocated and processed array of 10000000 integers, sum: 5000000");

        when(performanceTestService.runMemoryTest()).thenReturn(mockResult);

        // Perform request and verify response
        mockMvc.perform(get("/api/performance/memory"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.testType", is("Memory")))
                .andExpect(jsonPath("$.executionTimeMs", is(500)))
                .andExpect(jsonPath("$.memoryUsedBytes", is(41943040)))
                .andExpect(jsonPath("$.cpuCount", is(4)))
                .andExpect(jsonPath("$.threadCount", is(1)))
                .andExpect(jsonPath("$.additionalInfo", containsString("array")));

        verify(performanceTestService, times(1)).runMemoryTest();
    }

    @Test
    public void testRunConcurrencyTest() throws Exception {
        // Prepare mock result
        PerformanceTestResult mockResult = new PerformanceTestResult(
                "Concurrency", 800L, 2097152L, 4, 4, "Found 78498 prime numbers using 4 threads");

        when(performanceTestService.runConcurrencyTest()).thenReturn(mockResult);

        // Perform request and verify response
        mockMvc.perform(get("/api/performance/concurrency"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.testType", is("Concurrency")))
                .andExpect(jsonPath("$.executionTimeMs", is(800)))
                .andExpect(jsonPath("$.memoryUsedBytes", is(2097152)))
                .andExpect(jsonPath("$.cpuCount", is(4)))
                .andExpect(jsonPath("$.threadCount", is(4)))
                .andExpect(jsonPath("$.additionalInfo", containsString("threads")));

        verify(performanceTestService, times(1)).runConcurrencyTest();
    }

    @Test
    public void testGetSystemInfo() throws Exception {
        mockMvc.perform(get("/api/performance/system-info"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.availableProcessors", greaterThan(0)))
                .andExpect(jsonPath("$.maxMemory", greaterThan(0)))
                .andExpect(jsonPath("$.totalMemory", greaterThan(0)))
                .andExpect(jsonPath("$.freeMemory", greaterThan(0)))
                .andExpect(jsonPath("$.javaVersion", notNullValue()))
                .andExpect(jsonPath("$.javaVendor", notNullValue()))
                .andExpect(jsonPath("$.osName", notNullValue()))
                .andExpect(jsonPath("$.osVersion", notNullValue()));
    }
}