package com.example.app.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Model class for CORBA test data.
 * This class is designed to be used with CORBA, which was included in Java 8 but removed in Java 11+.
 */
public class CorbaTestData implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String id;
    private String name;
    private String description;
    private long timestamp;
    private List<DataPoint> dataPoints;
    
    public CorbaTestData() {
        this.dataPoints = new ArrayList<>();
    }
    
    public CorbaTestData(String id, String name, String description, long timestamp) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.timestamp = timestamp;
        this.dataPoints = new ArrayList<>();
    }
    
    /**
     * Nested class for data points.
     */
    public static class DataPoint implements Serializable {
        
        private static final long serialVersionUID = 1L;
        
        private String key;
        private double value;
        private String unit;
        
        public DataPoint() {
            // Default constructor
        }
        
        public DataPoint(String key, double value, String unit) {
            this.key = key;
            this.value = value;
            this.unit = unit;
        }
        
        public String getKey() {
            return key;
        }
        
        public void setKey(String key) {
            this.key = key;
        }
        
        public double getValue() {
            return value;
        }
        
        public void setValue(double value) {
            this.value = value;
        }
        
        public String getUnit() {
            return unit;
        }
        
        public void setUnit(String unit) {
            this.unit = unit;
        }
        
        @Override
        public String toString() {
            return key + ": " + value + " " + unit;
        }
    }
    
    // Getters and setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public long getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
    
    public List<DataPoint> getDataPoints() {
        return dataPoints;
    }
    
    public void setDataPoints(List<DataPoint> dataPoints) {
        this.dataPoints = dataPoints;
    }
    
    public void addDataPoint(String key, double value, String unit) {
        this.dataPoints.add(new DataPoint(key, value, unit));
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("CorbaTestData [id=").append(id)
          .append(", name=").append(name)
          .append(", description=").append(description)
          .append(", timestamp=").append(timestamp)
          .append(", dataPoints=").append(dataPoints.size())
          .append("]");
        return sb.toString();
    }
}