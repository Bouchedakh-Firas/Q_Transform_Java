package com.example.app.models;

/**
 * Model class for Java version information.
 */
public class JavaVersion {
    private String version;
    private String vendor;
    private String vmName;
    private String vmVersion;
    private String runtimeName;

    public JavaVersion() {
        // Default constructor
    }

    public JavaVersion(String version, String vendor, String vmName, String vmVersion, String runtimeName) {
        this.version = version;
        this.vendor = vendor;
        this.vmName = vmName;
        this.vmVersion = vmVersion;
        this.runtimeName = runtimeName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getVmName() {
        return vmName;
    }

    public void setVmName(String vmName) {
        this.vmName = vmName;
    }

    public String getVmVersion() {
        return vmVersion;
    }

    public void setVmVersion(String vmVersion) {
        this.vmVersion = vmVersion;
    }

    public String getRuntimeName() {
        return runtimeName;
    }

    public void setRuntimeName(String runtimeName) {
        this.runtimeName = runtimeName;
    }
}