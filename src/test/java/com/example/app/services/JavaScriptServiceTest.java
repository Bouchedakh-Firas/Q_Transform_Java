package com.example.app.services;

import com.example.app.models.JavaScriptResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class JavaScriptServiceTest {

    @Autowired
    private JavaScriptService javaScriptService;

    @Test
    public void testExecuteSimpleScript() throws Exception {
        String script = "1 + 1";
        Object result = javaScriptService.executeScript(script);
        assertEquals(2.0, result, "Expected the script to return 2.0");
    }

    @Test
    public void testExecuteComplexScript() {
        // Given
        String script = "function fibonacci(n) { if (n <= 1) return n; return fibonacci(n-1) + fibonacci(n-2); } fibonacci(10);";

        // When
        JavaScriptResult result = javaScriptService.executeScript(script);

        // Then
        assertTrue(result.isSuccess());
        assertEquals(55.0, result.getResult());
        assertTrue(result.getExecutionTimeMs() >= 0);
        assertNull(result.getErrorMessage());
    }

    @Test
    public void testExecuteInvalidScript() {
        String script = "function broken() { return x +; }; broken();";
        assertThrows(Exception.class, () -> javaScriptService.executeScript(script),
                "Expected an exception for invalid script");
    }

    @Test
    public void testExecuteScriptWithTimeout() {
        // Given
        String script = "var sum = 0; for (var i = 0; i < 1000; i++) { sum += i; } sum;";
        int timeout = 5;

        // When
        JavaScriptResult result = javaScriptService.executeScriptWithTimeout(script, timeout);

        // Then
        assertTrue(result.isSuccess());
        assertEquals(499500.0, result.getResult());
        assertTrue(result.getExecutionTimeMs() >= 0);
        assertNull(result.getErrorMessage());
    }

    @Test
    public void testRunPerformanceTest() {
        // When
        JavaScriptResult result = javaScriptService.runPerformanceTest();

        // Then
        assertTrue(result.isSuccess());
        assertNotNull(result.getResult());
        assertTrue(result.getExecutionTimeMs() > 0);
        assertNull(result.getErrorMessage());
    }
}