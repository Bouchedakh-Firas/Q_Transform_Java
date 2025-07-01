package com.example.app.models;

/**
 * Model class for JavaScript execution results.
 */
public class JavaScriptResult {
    private String script;
    private Object result;
    private long executionTimeMs;
    private boolean success;
    private String errorMessage;

    public JavaScriptResult() {
        // Default constructor
    }

    public JavaScriptResult(String script, Object result, long executionTimeMs, boolean success, String errorMessage) {
        this.script = script;
        this.result = result;
        this.executionTimeMs = executionTimeMs;
        this.success = success;
        this.errorMessage = errorMessage;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public long getExecutionTimeMs() {
        return executionTimeMs;
    }

    public void setExecutionTimeMs(long executionTimeMs) {
        this.executionTimeMs = executionTimeMs;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}