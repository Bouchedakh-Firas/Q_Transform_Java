package com.example.app.models;

/**
 * Model class representing a restaurant.
 */
public class Restaurant {
    private String name;
    private String address;
    private String cuisineType;
    private double rating;
    private double distance;
    private String[] dietaryOptions;
    private String phoneNumber;
    private String website;

    public Restaurant() {
    }

    public Restaurant(String name, String address, String cuisineType, double rating, 
                     double distance, String[] dietaryOptions, String phoneNumber, String website) {
        this.name = name;
        this.address = address;
        this.cuisineType = cuisineType;
        this.rating = rating;
        this.distance = distance;
        this.dietaryOptions = dietaryOptions;
        this.phoneNumber = phoneNumber;
        this.website = website;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCuisineType() {
        return cuisineType;
    }

    public void setCuisineType(String cuisineType) {
        this.cuisineType = cuisineType;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String[] getDietaryOptions() {
        return dietaryOptions;
    }

    public void setDietaryOptions(String[] dietaryOptions) {
        this.dietaryOptions = dietaryOptions;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
}