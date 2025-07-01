package com.example.app.models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

/**
 * Model class for XML test data.
 * This class uses JAXB annotations for XML binding.
 * JAXB was included in Java 8 but removed in Java 11+.
 */
@XmlRootElement(name = "testData")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "XmlTestData", propOrder = {
    "id",
    "name",
    "description",
    "creationDate",
    "properties",
    "items"
})
public class XmlTestData {
    
    @XmlElement(required = true)
    private String id;
    
    @XmlElement(required = true)
    private String name;
    
    @XmlElement(required = false)
    private String description;
    
    @XmlElement(required = true)
    private String creationDate;
    
    @XmlElement(required = false)
    private List<Property> properties;
    
    @XmlElement(required = false)
    private List<Item> items;
    
    public XmlTestData() {
        // Default constructor required by JAXB
        this.properties = new ArrayList<>();
        this.items = new ArrayList<>();
    }
    
    public XmlTestData(String id, String name, String description, String creationDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.creationDate = creationDate;
        this.properties = new ArrayList<>();
        this.items = new ArrayList<>();
    }
    
    // Nested class for properties
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "Property", propOrder = {
        "key",
        "value"
    })
    public static class Property {
        
        @XmlElement(required = true)
        private String key;
        
        @XmlElement(required = true)
        private String value;
        
        public Property() {
            // Default constructor required by JAXB
        }
        
        public Property(String key, String value) {
            this.key = key;
            this.value = value;
        }
        
        public String getKey() {
            return key;
        }
        
        public void setKey(String key) {
            this.key = key;
        }
        
        public String getValue() {
            return value;
        }
        
        public void setValue(String value) {
            this.value = value;
        }
    }
    
    // Nested class for items
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "Item", propOrder = {
        "itemId",
        "itemName",
        "quantity"
    })
    public static class Item {
        
        @XmlElement(required = true)
        private String itemId;
        
        @XmlElement(required = true)
        private String itemName;
        
        @XmlElement(required = true)
        private int quantity;
        
        public Item() {
            // Default constructor required by JAXB
        }
        
        public Item(String itemId, String itemName, int quantity) {
            this.itemId = itemId;
            this.itemName = itemName;
            this.quantity = quantity;
        }
        
        public String getItemId() {
            return itemId;
        }
        
        public void setItemId(String itemId) {
            this.itemId = itemId;
        }
        
        public String getItemName() {
            return itemName;
        }
        
        public void setItemName(String itemName) {
            this.itemName = itemName;
        }
        
        public int getQuantity() {
            return quantity;
        }
        
        public void setQuantity(int quantity) {
            this.quantity = quantity;
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
    
    public String getCreationDate() {
        return creationDate;
    }
    
    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }
    
    public List<Property> getProperties() {
        return properties;
    }
    
    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }
    
    public void addProperty(String key, String value) {
        this.properties.add(new Property(key, value));
    }
    
    public List<Item> getItems() {
        return items;
    }
    
    public void setItems(List<Item> items) {
        this.items = items;
    }
    
    public void addItem(String itemId, String itemName, int quantity) {
        this.items.add(new Item(itemId, itemName, quantity));
    }
}