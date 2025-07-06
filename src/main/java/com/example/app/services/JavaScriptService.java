package com.example.app.services;

import com.example.app.models.JavaScriptResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.concurrent.TimeUnit;

/**
 * Service for executing JavaScript code using the Nashorn engine.
 * This uses Java 8's built-in Nashorn engine without requiring any external
 * dependencies.
 * Note: Nashorn was deprecated in Java 11 and removed in Java 17.
 */
@Service
public class JavaScriptService {

    private static final Logger logger = LoggerFactory.getLogger(JavaScriptService.class);
    private final ScriptEngine engine;

    public JavaScriptService() {
        // Initialize the ScriptEngine
        ScriptEngineManager manager = new ScriptEngineManager();
        this.engine = manager.getEngineByName("JavaScript");
        if (this.engine == null) {
            throw new IllegalStateException("JavaScript engine not available");
        }
        logger.info("Initialized JavaScript engine: {}", engine != null);
    }

    /**
     * Execute a JavaScript script and return the result.
     *
     * @param script The JavaScript code to execute
     * @return A JavaScriptResult object containing the execution result
     */
    public JavaScriptResult executeScript(String script) {
        logger.info("Executing JavaScript script: {}", script);

        long startTime = System.currentTimeMillis();

        try {
            Object result = engine.eval(script);
            long executionTime = System.currentTimeMillis() - startTime;

            logger.info("Script executed successfully in {} ms", executionTime);
            return new JavaScriptResult(script, result, executionTime, true, null);
        } catch (ScriptException e) {
            long executionTime = System.currentTimeMillis() - startTime;

            logger.error("Error executing script: {}", e.getMessage(), e);
            return new JavaScriptResult(script, null, executionTime, false, e.getMessage());
        }
    }

    /**
     * Run a JavaScript performance test by executing a complex script multiple
     * times.
     *
     * @return A JavaScriptResult object containing the execution result
     */
    public JavaScriptResult runPerformanceTest() {
        logger.info("Running JavaScript performance test");

        // Create a complex JavaScript script for performance testing
        StringBuilder scriptBuilder = new StringBuilder();

        // Add function to calculate Fibonacci numbers
        scriptBuilder.append("function fibonacci(n) {\n");
        scriptBuilder.append("  if (n <= 1) return n;\n");
        scriptBuilder.append("  return fibonacci(n-1) + fibonacci(n-2);\n");
        scriptBuilder.append("}\n\n");

        // Add function to check if a number is prime
        scriptBuilder.append("function isPrime(n) {\n");
        scriptBuilder.append("  if (n <= 1) return false;\n");
        scriptBuilder.append("  if (n <= 3) return true;\n");
        scriptBuilder.append("  if (n % 2 === 0 || n % 3 === 0) return false;\n");
        scriptBuilder.append("  for (let i = 5; i * i <= n; i += 6) {\n");
        scriptBuilder.append("    if (n % i === 0 || n % (i + 2) === 0) return false;\n");
        scriptBuilder.append("  }\n");
        scriptBuilder.append("  return true;\n");
        scriptBuilder.append("}\n\n");

        // Add function to sort an array
        scriptBuilder.append("function bubbleSort(arr) {\n");
        scriptBuilder.append("  const n = arr.length;\n");
        scriptBuilder.append("  for (let i = 0; i < n - 1; i++) {\n");
        scriptBuilder.append("    for (let j = 0; j < n - i - 1; j++) {\n");
        scriptBuilder.append("      if (arr[j] > arr[j + 1]) {\n");
        scriptBuilder.append("        const temp = arr[j];\n");
        scriptBuilder.append("        arr[j] = arr[j + 1];\n");
        scriptBuilder.append("        arr[j + 1] = temp;\n");
        scriptBuilder.append("      }\n");
        scriptBuilder.append("    }\n");
        scriptBuilder.append("  }\n");
        scriptBuilder.append("  return arr;\n");
        scriptBuilder.append("}\n\n");

        // Add test execution code
        scriptBuilder.append("// Performance test execution\n");
        scriptBuilder.append("const results = {};\n\n");

        // Test 1: Calculate Fibonacci numbers
        scriptBuilder.append("// Test 1: Fibonacci calculation\n");
        scriptBuilder.append("const fibStart = new Date().getTime();\n");
        scriptBuilder.append("const fibResults = [];\n");
        scriptBuilder.append("for (let i = 1; i <= 25; i++) {\n");
        scriptBuilder.append("  fibResults.push(fibonacci(i));\n");
        scriptBuilder.append("}\n");
        scriptBuilder.append("const fibEnd = new Date().getTime();\n");
        scriptBuilder.append("results.fibonacciTime = fibEnd - fibStart;\n");
        scriptBuilder.append("results.fibonacciResults = fibResults;\n\n");

        // Test 2: Find prime numbers
        scriptBuilder.append("// Test 2: Prime number calculation\n");
        scriptBuilder.append("const primeStart = new Date().getTime();\n");
        scriptBuilder.append("const primes = [];\n");
        scriptBuilder.append("for (let i = 2; i <= 1000; i++) {\n");
        scriptBuilder.append("  if (isPrime(i)) {\n");
        scriptBuilder.append("    primes.push(i);\n");
        scriptBuilder.append("  }\n");
        scriptBuilder.append("}\n");
        scriptBuilder.append("const primeEnd = new Date().getTime();\n");
        scriptBuilder.append("results.primeTime = primeEnd - primeStart;\n");
        scriptBuilder.append("results.primeCount = primes.length;\n\n");

        // Test 3: Array sorting
        scriptBuilder.append("// Test 3: Array sorting\n");
        scriptBuilder.append("const sortStart = new Date().getTime();\n");
        scriptBuilder.append("const arrays = [];\n");
        scriptBuilder.append("for (let i = 0; i < 10; i++) {\n");
        scriptBuilder.append("  const arr = [];\n");
        scriptBuilder.append("  for (let j = 0; j < 500; j++) {\n");
        scriptBuilder.append("    arr.push(Math.floor(Math.random() * 1000));\n");
        scriptBuilder.append("  }\n");
        scriptBuilder.append("  arrays.push(bubbleSort(arr));\n");
        scriptBuilder.append("}\n");
        scriptBuilder.append("const sortEnd = new Date().getTime();\n");
        scriptBuilder.append("results.sortTime = sortEnd - sortStart;\n");
        scriptBuilder.append("results.sortedArraysCount = arrays.length;\n\n");

        // Test 4: String manipulation
        scriptBuilder.append("// Test 4: String manipulation\n");
        scriptBuilder.append("const strStart = new Date().getTime();\n");
        scriptBuilder.append("let longString = '';\n");
        scriptBuilder.append("for (let i = 0; i < 10000; i++) {\n");
        scriptBuilder.append("  longString += 'a';\n");
        scriptBuilder.append("}\n");
        scriptBuilder.append("const words = [];\n");
        scriptBuilder.append("for (let i = 0; i < 1000; i++) {\n");
        scriptBuilder.append("  words.push('word' + i);\n");
        scriptBuilder.append("}\n");
        scriptBuilder.append("const joinedString = words.join(' ');\n");
        scriptBuilder.append("const splitWords = joinedString.split(' ');\n");
        scriptBuilder.append("const strEnd = new Date().getTime();\n");
        scriptBuilder.append("results.stringTime = strEnd - strStart;\n");
        scriptBuilder.append("results.stringLength = longString.length;\n");
        scriptBuilder.append("results.wordsCount = splitWords.length;\n\n");

        // Return results
        scriptBuilder.append("// Return overall results\n");
        scriptBuilder.append(
                "results.totalTime = results.fibonacciTime + results.primeTime + results.sortTime + results.stringTime;\n");
        scriptBuilder.append("results;\n");

        String script = scriptBuilder.toString();
        return executeScript(script);
    }

    /**
     * Execute a JavaScript script with a timeout.
     *
     * @param script         The JavaScript code to execute
     * @param timeoutSeconds Maximum execution time in seconds
     * @return A JavaScriptResult object containing the execution result
     */
    public JavaScriptResult executeScriptWithTimeout(String script, int timeoutSeconds) {
        logger.info("Executing JavaScript script with {} second timeout: {}", timeoutSeconds, script);

        long startTime = System.currentTimeMillis();

        // Create a thread to execute the script
        final Object[] resultHolder = new Object[1];
        final Exception[] exceptionHolder = new Exception[1];

        Thread scriptThread = new Thread(() -> {
            try {
                resultHolder[0] = engine.eval(script);
            } catch (Exception e) {
                exceptionHolder[0] = e;
            }
        });

        // Start the thread and wait for it to complete
        scriptThread.start();
        try {
            scriptThread.join(TimeUnit.SECONDS.toMillis(timeoutSeconds));

            // Check if the thread is still alive (timeout occurred)
            if (scriptThread.isAlive()) {
                // Interrupt the thread (may not stop the script execution)
                scriptThread.interrupt();

                long executionTime = System.currentTimeMillis() - startTime;
                logger.error("Script execution timed out after {} ms", executionTime);
                return new JavaScriptResult(script, null, executionTime, false,
                        "Script execution timed out after " + timeoutSeconds + " seconds");
            }

            // Check if an exception occurred
            if (exceptionHolder[0] != null) {
                long executionTime = System.currentTimeMillis() - startTime;
                logger.error("Error executing script: {}", exceptionHolder[0].getMessage(), exceptionHolder[0]);
                return new JavaScriptResult(script, null, executionTime, false, exceptionHolder[0].getMessage());
            }

            // Script executed successfully
            long executionTime = System.currentTimeMillis() - startTime;
            logger.info("Script executed successfully in {} ms", executionTime);
            return new JavaScriptResult(script, resultHolder[0], executionTime, true, null);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            long executionTime = System.currentTimeMillis() - startTime;
            logger.error("Thread interrupted while waiting for script execution", e);
            return new JavaScriptResult(script, null, executionTime, false, "Thread interrupted: " + e.getMessage());
        }
    }
}