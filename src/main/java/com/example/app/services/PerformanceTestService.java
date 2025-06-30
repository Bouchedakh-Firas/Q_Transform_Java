package com.example.app.services;

import com.example.app.models.PerformanceTestResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Service for running performance tests.
 * Enhanced with additional Java-specific benchmarks and increased workloads.
 */
@Service
public class PerformanceTestService {

    private static final Logger logger = LoggerFactory.getLogger(PerformanceTestService.class);
    private static final int DEFAULT_TEST_DURATION_MS = 5000; // 5 seconds
    
    // CPU test constants
    private static final int MAX_PRIME = 2000000; // Increased from 1,000,000 to 2,000,000
    
    // Memory test constants
    private static final int MEMORY_ARRAY_SIZE = 20000000; // Increased from 10 million to 20 million integers (about 80MB)
    
    // Concurrency test constants
    private static final int CONCURRENCY_TASKS = 20; // Increased from 10 to 20
    
    // Stream operations test constants
    private static final int STREAM_DATA_SIZE = 10000000; // 10 million elements for stream operations
    
    // String operations test constants
    private static final int STRING_CONCAT_ITERATIONS = 100000; // Number of string concatenations
    private static final int STRING_BUILDER_ITERATIONS = 1000000; // Number of StringBuilder appends
    
    // Collections operations test constants
    private static final int COLLECTION_SIZE = 1000000; // Size of collections for testing
    
    // File I/O test constants
    private static final int FILE_IO_LINES = 100000; // Number of lines to write/read
    private static final String TEMP_DIR = System.getProperty("java.io.tmpdir");

    /**
     * Run a CPU-intensive test by calculating prime numbers.
     * Enhanced with additional calculations to increase workload.
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
        
        // Additional CPU-intensive work: Calculate Fibonacci numbers
        long fibResult = calculateFibonacci(40); // Calculate 40th Fibonacci number
        
        // Additional CPU-intensive work: Calculate factorial
        long factResult = calculateFactorial(20); // Calculate 20!
        
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
            "Found " + primeCount + " prime numbers up to " + MAX_PRIME + 
            ", Fibonacci(40)=" + fibResult + 
            ", Factorial(20)=" + factResult
        );
        
        logger.info("CPU test completed in {} ms", executionTime);
        return result;
    }
    
    /**
     * Run a memory-intensive test by allocating and manipulating large arrays.
     * Enhanced with additional memory operations to increase workload.
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
        
        // Additional memory-intensive work: Create and manipulate a large 2D array
        int[][] matrix = new int[2000][2000]; // 4 million integers
        for (int i = 0; i < 2000; i++) {
            for (int j = 0; j < 2000; j++) {
                matrix[i][j] = random.nextInt(100);
            }
        }
        
        // Perform matrix operations
        int matrixSum = 0;
        for (int i = 0; i < 2000; i++) {
            for (int j = 0; j < 2000; j++) {
                matrixSum += matrix[i][j];
            }
        }
        
        // Additional memory-intensive work: Create and manipulate a large list of objects
        List<String> largeList = new ArrayList<>(1000000);
        for (int i = 0; i < 1000000; i++) {
            largeList.add(UUID.randomUUID().toString());
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
            "Allocated and processed array of " + MEMORY_ARRAY_SIZE + " integers, sum: " + sum + 
            ", Matrix sum: " + matrixSum + 
            ", List size: " + largeList.size()
        );
        
        logger.info("Memory test completed in {} ms", executionTime);
        return result;
    }
    
    /**
     * Run a concurrency test by executing multiple parallel tasks.
     * Enhanced with additional concurrent operations to increase workload.
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
        
        // Create tasks for prime number calculation
        List<CompletableFuture<Integer>> primeFutures = new ArrayList<>();
        for (int i = 0; i < threadCount; i++) {
            final int taskId = i;
            CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
                // Each task finds primes in a different range
                int start = taskId * (MAX_PRIME / threadCount);
                int end = (taskId + 1) * (MAX_PRIME / threadCount);
                return countPrimesInRange(start, end);
            }, executor);
            primeFutures.add(future);
        }
        
        // Create tasks for matrix multiplication
        List<CompletableFuture<Long>> matrixFutures = new ArrayList<>();
        for (int i = 0; i < threadCount / 2; i++) {
            CompletableFuture<Long> future = CompletableFuture.supplyAsync(() -> {
                // Create and multiply matrices
                return multiplyMatrices(500);
            }, executor);
            matrixFutures.add(future);
        }
        
        // Create tasks for string operations
        List<CompletableFuture<Integer>> stringFutures = new ArrayList<>();
        for (int i = 0; i < threadCount / 2; i++) {
            CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
                // Perform string operations
                return performStringOperations(50000);
            }, executor);
            stringFutures.add(future);
        }
        
        // Create a shared concurrent map for testing concurrent collections
        ConcurrentHashMap<Integer, String> concurrentMap = new ConcurrentHashMap<>();
        
        // Create tasks for concurrent map operations
        List<CompletableFuture<Integer>> mapFutures = new ArrayList<>();
        for (int i = 0; i < threadCount; i++) {
            final int taskId = i;
            CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
                // Each task adds entries to the concurrent map
                int start = taskId * 10000;
                int end = start + 10000;
                for (int j = start; j < end; j++) {
                    concurrentMap.put(j, "Value-" + j);
                }
                return end - start;
            }, executor);
            mapFutures.add(future);
        }
        
        // Wait for all tasks to complete and collect results
        int totalPrimes = 0;
        for (CompletableFuture<Integer> future : primeFutures) {
            try {
                totalPrimes += future.get(30, TimeUnit.SECONDS); // Increased timeout to 30 seconds
            } catch (Exception e) {
                logger.error("Error in prime calculation task", e);
            }
        }
        
        long totalMatrixSum = 0;
        for (CompletableFuture<Long> future : matrixFutures) {
            try {
                totalMatrixSum += future.get(30, TimeUnit.SECONDS);
            } catch (Exception e) {
                logger.error("Error in matrix multiplication task", e);
            }
        }
        
        int totalStringOps = 0;
        for (CompletableFuture<Integer> future : stringFutures) {
            try {
                totalStringOps += future.get(30, TimeUnit.SECONDS);
            } catch (Exception e) {
                logger.error("Error in string operations task", e);
            }
        }
        
        int totalMapEntries = 0;
        for (CompletableFuture<Integer> future : mapFutures) {
            try {
                totalMapEntries += future.get(30, TimeUnit.SECONDS);
            } catch (Exception e) {
                logger.error("Error in map operations task", e);
            }
        }
        
        // Shutdown executor
        executor.shutdown();
        try {
            executor.awaitTermination(10, TimeUnit.SECONDS); // Increased timeout to 10 seconds
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
            "Found " + totalPrimes + " prime numbers, " +
            "Matrix sum: " + totalMatrixSum + ", " +
            "String operations: " + totalStringOps + ", " +
            "Map entries: " + totalMapEntries + ", " +
            "Using " + threadCount + " threads"
        );
        
        logger.info("Concurrency test completed in {} ms with {} threads", executionTime, threadCount);
        return result;
    }
    
    /**
     * Run a Stream API performance test.
     * Tests various Stream operations that were introduced in Java 8.
     * 
     * @return PerformanceTestResult with test metrics
     */
    public PerformanceTestResult runStreamTest() {
        logger.info("Starting Stream API performance test");
        
        // Get initial memory usage
        Runtime runtime = Runtime.getRuntime();
        long startMemory = runtime.totalMemory() - runtime.freeMemory();
        
        // Start timing
        long startTime = System.currentTimeMillis();
        
        // Create a large list of random integers
        List<Integer> numbers = new ArrayList<>(STREAM_DATA_SIZE);
        Random random = new Random();
        for (int i = 0; i < STREAM_DATA_SIZE; i++) {
            numbers.add(random.nextInt(10000));
        }
        
        // Test 1: Filter and count
        long evenCount = numbers.stream()
                .filter(n -> n % 2 == 0)
                .count();
        
        // Test 2: Map and collect
        List<String> stringList = numbers.stream()
                .limit(1000000) // Limit to 1 million to avoid excessive memory usage
                .map(n -> "Number: " + n)
                .collect(Collectors.toList());
        
        // Test 3: Sort
        List<Integer> sortedList = numbers.stream()
                .limit(1000000) // Limit to 1 million to avoid excessive sorting time
                .sorted()
                .collect(Collectors.toList());
        
        // Test 4: Parallel stream with reduction
        long sum = numbers.parallelStream()
                .mapToLong(Integer::longValue)
                .sum();
        
        // Test 5: Group by
        Map<Integer, Long> groupedByRemainder = numbers.stream()
                .limit(5000000) // Limit to 5 million to avoid excessive memory usage
                .collect(Collectors.groupingBy(
                        n -> n % 10,
                        Collectors.counting()
                ));
        
        // Test 6: Find first matching element
        Optional<Integer> firstLargeNumber = numbers.stream()
                .filter(n -> n > 9000)
                .findFirst();
        
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
            "Stream",
            executionTime,
            memoryUsed,
            cpuCount,
            1, // Single thread for stream test (though parallel streams use multiple threads internally)
            "Processed " + STREAM_DATA_SIZE + " elements, " +
            "Even count: " + evenCount + ", " +
            "String list size: " + stringList.size() + ", " +
            "Sorted list size: " + sortedList.size() + ", " +
            "Sum: " + sum + ", " +
            "Groups: " + groupedByRemainder.size() + ", " +
            "First large number: " + (firstLargeNumber.isPresent() ? firstLargeNumber.get() : "none")
        );
        
        logger.info("Stream API test completed in {} ms", executionTime);
        return result;
    }
    
    /**
     * Run a String operations performance test.
     * Tests various String operations that might perform differently across Java versions.
     * 
     * @return PerformanceTestResult with test metrics
     */
    public PerformanceTestResult runStringTest() {
        logger.info("Starting String operations performance test");
        
        // Get initial memory usage
        Runtime runtime = Runtime.getRuntime();
        long startMemory = runtime.totalMemory() - runtime.freeMemory();
        
        // Start timing
        long startTime = System.currentTimeMillis();
        
        // Test 1: String concatenation
        String concatResult = "";
        for (int i = 0; i < STRING_CONCAT_ITERATIONS; i++) {
            concatResult += "a";
        }
        
        // Test 2: StringBuilder
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < STRING_BUILDER_ITERATIONS; i++) {
            sb.append("a");
        }
        String builderResult = sb.toString();
        
        // Test 3: String.format
        List<String> formattedStrings = new ArrayList<>(10000);
        for (int i = 0; i < 10000; i++) {
            formattedStrings.add(String.format("Number: %d, Hex: %08X, Float: %.2f", i, i, i / 100.0f));
        }
        
        // Test 4: String splitting
        String longText = String.join(",", Collections.nCopies(100000, "word"));
        String[] splitResult = longText.split(",");
        
        // Test 5: String comparison
        List<String> words = new ArrayList<>(100000);
        for (int i = 0; i < 100000; i++) {
            words.add(UUID.randomUUID().toString());
        }
        Collections.sort(words);
        
        // Test 6: Regular expressions
        String pattern = "[a-z0-9]+@[a-z0-9]+\\.[a-z]{2,3}";
        List<String> emails = Arrays.asList(
                "test@example.com",
                "invalid.email",
                "another@test.org",
                "not_an_email",
                "email@domain.com"
        );
        
        long validEmails = emails.stream()
                .filter(email -> email.matches(pattern))
                .count();
        
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
            "String",
            executionTime,
            memoryUsed,
            cpuCount,
            1, // Single thread for string test
            "Concat length: " + concatResult.length() + ", " +
            "StringBuilder length: " + builderResult.length() + ", " +
            "Formatted strings: " + formattedStrings.size() + ", " +
            "Split result length: " + splitResult.length + ", " +
            "Sorted words: " + words.size() + ", " +
            "Valid emails: " + validEmails
        );
        
        logger.info("String operations test completed in {} ms", executionTime);
        return result;
    }
    
    /**
     * Run a Collections operations performance test.
     * Tests various Collections operations that might perform differently across Java versions.
     * 
     * @return PerformanceTestResult with test metrics
     */
    public PerformanceTestResult runCollectionsTest() {
        logger.info("Starting Collections operations performance test");
        
        // Get initial memory usage
        Runtime runtime = Runtime.getRuntime();
        long startMemory = runtime.totalMemory() - runtime.freeMemory();
        
        // Start timing
        long startTime = System.currentTimeMillis();
        
        Random random = new Random();
        
        // Test 1: ArrayList operations
        List<Integer> arrayList = new ArrayList<>(COLLECTION_SIZE);
        for (int i = 0; i < COLLECTION_SIZE; i++) {
            arrayList.add(random.nextInt(1000000));
        }
        
        // Sort the ArrayList
        Collections.sort(arrayList);
        
        // Binary search
        int searchCount = 0;
        for (int i = 0; i < 1000; i++) {
            int key = random.nextInt(1000000);
            int index = Collections.binarySearch(arrayList, key);
            if (index >= 0) {
                searchCount++;
            }
        }
        
        // Test 2: HashMap operations
        Map<Integer, String> hashMap = new HashMap<>(COLLECTION_SIZE);
        for (int i = 0; i < COLLECTION_SIZE; i++) {
            hashMap.put(i, "Value-" + i);
        }
        
        // Retrieve values
        int hitCount = 0;
        for (int i = 0; i < 100000; i++) {
            int key = random.nextInt(COLLECTION_SIZE * 2); // Some will miss
            String value = hashMap.get(key);
            if (value != null) {
                hitCount++;
            }
        }
        
        // Test 3: Stream operations on collections
        long evenSum = arrayList.stream()
                .filter(n -> n % 2 == 0)
                .mapToLong(Integer::longValue)
                .sum();
        
        // Test 4: Functional operations (forEach)
        final int[] counter = {0};
        hashMap.forEach((k, v) -> {
            if (k % 100 == 0) {
                counter[0]++;
            }
        });
        
        // Test 5: Collection to array conversion
        Integer[] array = arrayList.toArray(new Integer[0]);
        
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
            "Collections",
            executionTime,
            memoryUsed,
            cpuCount,
            1, // Single thread for collections test
            "ArrayList size: " + arrayList.size() + ", " +
            "Search hits: " + searchCount + ", " +
            "HashMap size: " + hashMap.size() + ", " +
            "HashMap hits: " + hitCount + ", " +
            "Even sum: " + evenSum + ", " +
            "Counter: " + counter[0] + ", " +
            "Array length: " + array.length
        );
        
        logger.info("Collections operations test completed in {} ms", executionTime);
        return result;
    }
    
    /**
     * Run a File I/O performance test.
     * Tests various File I/O operations that might perform differently across Java versions.
     * 
     * @return PerformanceTestResult with test metrics
     */
    public PerformanceTestResult runFileIOTest() {
        logger.info("Starting File I/O performance test");
        
        // Get initial memory usage
        Runtime runtime = Runtime.getRuntime();
        long startMemory = runtime.totalMemory() - runtime.freeMemory();
        
        // Start timing
        long startTime = System.currentTimeMillis();
        
        // Create temporary files
        File tempFile1 = null;
        File tempFile2 = null;
        Path tempPath = null;
        
        try {
            // Test 1: Traditional File I/O
            tempFile1 = File.createTempFile("perf-test-", ".txt", new File(TEMP_DIR));
            tempFile1.deleteOnExit();
            
            // Write to file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile1))) {
                for (int i = 0; i < FILE_IO_LINES; i++) {
                    writer.write("Line " + i + " of performance test file.\n");
                }
            }
            
            // Read from file
            List<String> lines1 = new ArrayList<>();
            try (java.util.Scanner scanner = new java.util.Scanner(tempFile1)) {
                while (scanner.hasNextLine()) {
                    lines1.add(scanner.nextLine());
                }
            }
            
            // Test 2: NIO File I/O
            tempPath = Files.createTempFile(Paths.get(TEMP_DIR), "perf-test-nio-", ".txt");
            
            // Write to file using NIO
            List<String> linesToWrite = new ArrayList<>();
            for (int i = 0; i < FILE_IO_LINES; i++) {
                linesToWrite.add("NIO Line " + i + " of performance test file.");
            }
            Files.write(tempPath, linesToWrite);
            
            // Read from file using NIO
            List<String> lines2 = Files.readAllLines(tempPath);
            
            // Test 3: File operations
            tempFile2 = File.createTempFile("perf-test-ops-", ".txt", new File(TEMP_DIR));
            tempFile2.deleteOnExit();
            
            // Get file attributes
            boolean exists = tempFile2.exists();
            long length = tempFile2.length();
            boolean isFile = tempFile2.isFile();
            String absolutePath = tempFile2.getAbsolutePath();
            
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
                "FileIO",
                executionTime,
                memoryUsed,
                cpuCount,
                1, // Single thread for file I/O test
                "Traditional I/O lines: " + lines1.size() + ", " +
                "NIO lines: " + lines2.size() + ", " +
                "File exists: " + exists + ", " +
                "File length: " + length + ", " +
                "Is file: " + isFile
            );
            
            logger.info("File I/O test completed in {} ms", executionTime);
            return result;
            
        } catch (IOException e) {
            logger.error("Error during File I/O test", e);
            
            // Return error result
            return new PerformanceTestResult(
                "FileIO",
                System.currentTimeMillis() - startTime,
                0,
                runtime.availableProcessors(),
                1,
                "Error during test: " + e.getMessage()
            );
        } finally {
            // Clean up temporary files
            try {
                if (tempPath != null) {
                    Files.deleteIfExists(tempPath);
                }
            } catch (IOException e) {
                logger.error("Error deleting temporary file", e);
            }
        }
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
    
    /**
     * Calculate the nth Fibonacci number.
     * 
     * @param n The position in the Fibonacci sequence
     * @return The nth Fibonacci number
     */
    private long calculateFibonacci(int n) {
        if (n <= 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        
        long prev = 0;
        long current = 1;
        
        for (int i = 2; i <= n; i++) {
            long next = prev + current;
            prev = current;
            current = next;
        }
        
        return current;
    }
    
    /**
     * Calculate factorial of n.
     * 
     * @param n The number to calculate factorial for
     * @return n!
     */
    private long calculateFactorial(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Factorial is not defined for negative numbers");
        }
        if (n <= 1) {
            return 1;
        }
        
        long result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        
        return result;
    }
    }
