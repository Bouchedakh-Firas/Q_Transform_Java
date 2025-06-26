package com.example.app.services;

import com.example.app.models.Restaurant;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.PlacesApi;
import com.google.maps.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RestaurantServiceTest {

    @Mock
    private GeoApiContext geoApiContext;

    @InjectMocks
    private RestaurantService restaurantService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        
        // Initialize the service with mock data
        List<Restaurant> mockRestaurants = Arrays.asList(
            new Restaurant(
                "Le Petit Bistro",
                "123 Rue de Paris, 75001 Paris",
                "Français",
                4.5,
                1.2,
                new String[]{"Végétarien", "Sans Gluten"},
                "+33 1 23 45 67 89",
                "http://www.lepetitbistro.fr"
            ),
            new Restaurant(
                "Sushi Sakura",
                "45 Avenue des Champs-Élysées, 75008 Paris",
                "Japonais",
                4.7,
                0.8,
                new String[]{"Végétarien", "Fruits de mer"},
                "+33 1 23 45 67 90",
                "http://www.sushisakura.fr"
            )
        );
        
        ReflectionTestUtils.setField(restaurantService, "mockRestaurants", mockRestaurants);
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