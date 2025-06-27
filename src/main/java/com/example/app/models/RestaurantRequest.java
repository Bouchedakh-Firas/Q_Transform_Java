package com.example.app.models;

import java.util.List;

/**
 * Model class representing a request for finding a restaurant.
 */
public class RestaurantRequest {
    private String address;
    private List<String> dietaryPreferences;
    private String foodType;

    public RestaurantRequest() {
    }

    public RestaurantRequest(String address, List<String> dietaryPreferences, String foodType) {
        this.address = address;
        this.dietaryPreferences = dietaryPreferences;
        this.foodType = foodType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<String> getDietaryPreferences() {
        return dietaryPreferences;
    }

    public void setDietaryPreferences(List<String> dietaryPreferences) {
        this.dietaryPreferences = dietaryPreferences;
    }
    
    public String getFoodType() {
        return foodType;
    }
    
    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }
}