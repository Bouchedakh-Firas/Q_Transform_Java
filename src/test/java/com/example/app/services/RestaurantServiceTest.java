package com.example.app.services;

import com.example.app.models.Restaurant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class RestaurantServiceTest {

    private RestaurantService restaurantService;

    @BeforeEach
    public void setUp() {
        restaurantService = new RestaurantService();
    }

    @Test
    public void findRandomRestaurantShouldReturnRestaurantWithNoPreferences() {
        // Given
        String address = "123 Test Street, Paris";
        List<String> dietaryPreferences = Collections.emptyList();

        // When
        Restaurant restaurant = restaurantService.findRandomRestaurant(address, dietaryPreferences);

        // Then
        assertThat(restaurant).isNotNull();
        assertThat(restaurant.getName()).isNotEmpty();
        assertThat(restaurant.getAddress()).isNotEmpty();
        assertThat(restaurant.getCuisineType()).isNotEmpty();
    }

    @Test
    public void findRandomRestaurantShouldReturnRestaurantWithVegetarianPreference() {
        // Given
        String address = "123 Test Street, Paris";
        List<String> dietaryPreferences = Collections.singletonList("végétarien");

        // When
        Restaurant restaurant = restaurantService.findRandomRestaurant(address, dietaryPreferences);

        // Then
        assertThat(restaurant).isNotNull();
        assertThat(restaurant.getName()).isNotEmpty();
        assertThat(restaurant.getAddress()).isNotEmpty();
        assertThat(restaurant.getCuisineType()).isNotEmpty();
        
        // Check if the restaurant has the vegetarian option
        boolean hasVegetarianOption = false;
        for (String option : restaurant.getDietaryOptions()) {
            if (option.equalsIgnoreCase("Végétarien")) {
                hasVegetarianOption = true;
                break;
            }
        }
        assertThat(hasVegetarianOption).isTrue();
    }

    @Test
    public void findRandomRestaurantShouldReturnNullWithNonExistentPreference() {
        // Given
        String address = "123 Test Street, Paris";
        List<String> dietaryPreferences = Collections.singletonList("non-existent-preference");

        // When
        Restaurant restaurant = restaurantService.findRandomRestaurant(address, dietaryPreferences);

        // Then
        assertThat(restaurant).isNull();
    }

    @Test
    public void findRandomRestaurantShouldReturnRestaurantWithMultiplePreferences() {
        // Given
        String address = "123 Test Street, Paris";
        List<String> dietaryPreferences = Arrays.asList("végétarien", "sans gluten");

        // When
        Restaurant restaurant = restaurantService.findRandomRestaurant(address, dietaryPreferences);

        // Then
        assertThat(restaurant).isNotNull();
        assertThat(restaurant.getName()).isNotEmpty();
        assertThat(restaurant.getAddress()).isNotEmpty();
        assertThat(restaurant.getCuisineType()).isNotEmpty();
        
        // Check if the restaurant has at least one of the dietary options
        boolean hasMatchingOption = false;
        for (String option : restaurant.getDietaryOptions()) {
            if (option.equalsIgnoreCase("Végétarien") || option.equalsIgnoreCase("Sans Gluten")) {
                hasMatchingOption = true;
                break;
            }
        }
        assertThat(hasMatchingOption).isTrue();
    }
}