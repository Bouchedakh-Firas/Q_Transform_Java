package com.example.app.services;

import com.example.app.models.PerformanceTestResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * Service for running performance tests.
 */
@Service
public class PerformanceTestService {

    private static final Logger logger = LoggerFactory.getLogger(PerformanceTestService.class);
    private static final int DEFAULT_TEST_DURATION_MS = 5000; // 5 seconds
    private static final int MAX_PRIME = 1000000;
    private static final int MEMORY_ARRAY_SIZE = 10000000; // 10 million integers (about 40MB)
    private static final int CONCURRENCY_TASKS = 10;

    /**
     * Run a CPU-intensive test by calculating prime numbers.
     * 
     * @return PerformanceTestResult with test metrics
     */
    public PerformanceTestResult runCpuTest() {
        logger.info("Starting CPU performance test");
        
        // Get initial memory usage
        Runtime runtime = Runtime.getRuntime();
        long startMemory = runtime.totalMemory() - runtime.freeMemory();
        
        // Start timing
        long startTime = System.currentTimeMillis();
        
        // CPU-intensive work: Find prime numbers
        int primeCount = countPrimes(MAX_PRIME);
        
        // End timing
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        
        // Get final memory usage
        long endMemory = runtime.totalMemory() - runtime.freeMemory();
        long memoryUsed = endMemory - startMemory;
        
        // Get CPU count
        int cpuCount = runtime.availableProcessors();
        
        // Create result
        PerformanceTestResult result = new PerformanceTestResult(
            "CPU",
            executionTime,
            memoryUsed,
            cpuCount,
            1, // Single thread for CPU test
            "Found " + primeCount + " prime numbers up to " + MAX_PRIME
        );
        
        logger.info("CPU test completed in {} ms", executionTime);
        return result;
    }
    
    /**
     * Run a memory-intensive test by allocating and manipulating large arrays.
     * 
     * @return PerformanceTestResult with test metrics
     */
    public PerformanceTestResult runMemoryTest() {
        logger.info("Starting memory performance test");
        
        // Get initial memory usage
        Runtime runtime = Runtime.getRuntime();
        System.gc(); // Suggest garbage collection before test
        long startMemory = runtime.totalMemory() - runtime.freeMemory();
        
        // Start timing
        long startTime = System.currentTimeMillis();
        
        // Memory-intensive work: Allocate and manipulate large arrays
        int[] largeArray = new int[MEMORY_ARRAY_SIZE];
        Random random = new Random();
        
        // Fill array with random numbers
        for (int i = 0; i < MEMORY_ARRAY_SIZE; i++) {
            largeArray[i] = random.nextInt(1000);
        }
        
        // Perform some operations on the array
        int sum = 0;
        for (int i = 0; i < MEMORY_ARRAY_SIZE; i++) {
            sum += largeArray[i];
        }
        
        // End timing
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        
        // Get final memory usage
        long endMemory = runtime.totalMemory() - runtime.freeMemory();
        long memoryUsed = endMemory - startMemory;
        
        // Get CPU count
        int cpuCount = runtime.availableProcessors();
        
        // Create result
        PerformanceTestResult result = new PerformanceTestResult(
            "Memory",
            executionTime,
            memoryUsed,
            cpuCount,
            1, // Single thread for memory test
            "Allocated and processed array of " + MEMORY_ARRAY_SIZE + " integers, sum: " + sum
        );
        
        logger.info("Memory test completed in {} ms", executionTime);
        return result;
    }
    
    /**
     * Run a concurrency test by executing multiple parallel tasks.
     * 
     * @return PerformanceTestResult with test metrics
     */
    public PerformanceTestResult runConcurrencyTest() {
        logger.info("Starting concurrency performance test");
        
        // Get initial memory usage
        Runtime runtime = Runtime.getRuntime();
        long startMemory = runtime.totalMemory() - runtime.freeMemory();
        
        // Start timing
        long startTime = System.currentTimeMillis();
        
        // Get CPU count
        int cpuCount = runtime.availableProcessors();
        int threadCount = Math.min(cpuCount * 2, CONCURRENCY_TASKS); // Use at most 2 threads per CPU core
        
        // Create thread pool
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        
        // Create tasks
        List<CompletableFuture<Integer>> futures = new ArrayList<>();
        for (int i = 0; i < threadCount; i++) {
            final int taskId = i;
            CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
                // Each task finds primes in a different range
                int start = taskId * (MAX_PRIME / threadCount);
                int end = (taskId + 1) * (MAX_PRIME / threadCount);
                return countPrimesInRange(start, end);
            }, executor);
            futures.add(future);
        }
        
        // Wait for all tasks to complete and collect results
        int totalPrimes = 0;
        for (CompletableFuture<Integer> future : futures) {
            try {
                totalPrimes += future.get(10, TimeUnit.SECONDS); // Timeout after 10 seconds
            } catch (Exception e) {
                logger.error("Error in concurrency task", e);
            }
        }
        
        // Shutdown executor
        executor.shutdown();
        try {
            executor.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            logger.error("Executor termination interrupted", e);
            Thread.currentThread().interrupt();
        }
        
        // End timing
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        
        // Get final memory usage
        long endMemory = runtime.totalMemory() - runtime.freeMemory();
        long memoryUsed = endMemory - startMemory;
        
        // Create result
        PerformanceTestResult result = new PerformanceTestResult(
            "Concurrency",
            executionTime,
            memoryUsed,
            cpuCount,
            threadCount,
            "Found " + totalPrimes + " prime numbers using " + threadCount + " threads"
        );
        
        logger.info("Concurrency test completed in {} ms with {} threads", executionTime, threadCount);
        return result;
    }
    
    /**
     * Count prime numbers up to the specified limit.
     * 
     * @param limit Upper limit for prime search
     * @return Count of prime numbers found
     */
    private int countPrimes(int limit) {
        return (int) IntStream.rangeClosed(2, limit)
                .filter(this::isPrime)
                .count();
    }
    
    /**
     * Count prime numbers in the specified range.
     * 
     * @param start Start of range (inclusive)
     * @param end End of range (exclusive)
     * @return Count of prime numbers found
     */
    private int countPrimesInRange(int start, int end) {
        return (int) IntStream.range(Math.max(2, start), end)
                .filter(this::isPrime)
                .count();
    }
    
    /**
     * Check if a number is prime.
     * 
     * @param n Number to check
     * @return true if the number is prime, false otherwise
     */
    private boolean isPrime(int n) {
        if (n <= 1) {
            return false;
        }
        if (n <= 3) {
            return true;
        }
        if (n % 2 == 0 || n % 3 == 0) {
            return false;
        }
        for (int i = 5; i * i <= n; i += 6) {
            if (n % i == 0 || n % (i + 2) == 0) {
                return false;
            }
        }
        return true;
    }
}