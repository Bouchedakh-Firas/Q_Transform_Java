package com.example.app.services;

import com.example.app.models.Restaurant;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.PlacesApi;
import com.google.maps.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Service class for finding restaurants based on location and dietary preferences.
 * Uses Google Places API to find restaurants near a given address.
 */
@Service
public class RestaurantService {
    
    private static final Logger logger = LoggerFactory.getLogger(RestaurantService.class);
    private final GeoApiContext geoApiContext;
    private final Random random;
    private final List<Restaurant> mockRestaurants;
    
    @Autowired
    public RestaurantService(GeoApiContext geoApiContext) {
        this.geoApiContext = geoApiContext;
        this.random = new Random();
        this.mockRestaurants = initializeMockRestaurants();
    }
    
    /**
     * Finds a random restaurant near the specified address that matches the dietary preferences.
     * 
     * @param address The user's address
     * @param dietaryPreferences List of dietary preferences
     * @return A random restaurant that matches the criteria, or null if none found
     */
    public Restaurant findRandomRestaurant(String address, List<String> dietaryPreferences) {
        try {
            // Try to geocode the address
            GeocodingResult[] results = GeocodingApi.geocode(geoApiContext, address).await();
            if (results.length == 0) {
                logger.error("Could not geocode address: {}", address);
                return getRandomMockRestaurant(dietaryPreferences);
            }
            
            // Get the location from the geocoding result
            LatLng location = results[0].geometry.location;
            
            // Search for nearby restaurants
            PlacesSearchResponse response = PlacesApi.nearbySearchQuery(geoApiContext, location)
                    .radius(5000)
                    .type(PlaceType.RESTAURANT)
                    .await();
            
            if (response.results.length == 0) {
                logger.info("No restaurants found near address: {}", address);
                return getRandomMockRestaurant(dietaryPreferences);
            }
            
            // Convert the results to Restaurant objects
            List<Restaurant> restaurants = new ArrayList<>();
            for (PlacesSearchResult result : response.results) {
                try {
                    // Get more details about the place
                    PlaceDetails details = PlacesApi.placeDetails(geoApiContext, result.placeId).await();
                    
                    // Create a Restaurant object
                    Restaurant restaurant = new Restaurant(
                        result.name,
                        result.vicinity,
                        getRestaurantType(result),
                        result.rating,
                        calculateDistance(location, result.geometry.location),
                        getDietaryOptions(result),
                        details.formattedPhoneNumber != null ? details.formattedPhoneNumber : "",
                        details.website != null ? details.website.toString() : ""
                    );
                    
                    restaurants.add(restaurant);
                } catch (Exception e) {
                    logger.error("Error getting details for restaurant: {}", result.name, e);
                }
            }
            
            // Filter restaurants based on dietary preferences
            List<Restaurant> matchingRestaurants;
            if (dietaryPreferences == null || dietaryPreferences.isEmpty()) {
                matchingRestaurants = restaurants;
            } else {
                matchingRestaurants = filterByDietaryPreferences(restaurants, dietaryPreferences);
            }
            
            if (matchingRestaurants.isEmpty()) {
                logger.info("No restaurants matching dietary preferences found near address: {}", address);
                return getRandomMockRestaurant(dietaryPreferences);
            }
            
            // Return a random restaurant from the matching ones
            int index = random.nextInt(matchingRestaurants.size());
            return matchingRestaurants.get(index);
        } catch (Exception e) {
            logger.error("Error finding restaurant near address: {}", address, e);
            return getRandomMockRestaurant(dietaryPreferences);
        }
    }
    
    /**
     * Gets a random restaurant from the mock data that matches the dietary preferences.
     * 
     * @param dietaryPreferences List of dietary preferences
     * @return A random restaurant that matches the criteria, or null if none found
     */
    private Restaurant getRandomMockRestaurant(List<String> dietaryPreferences) {
        logger.info("Using mock restaurant data");
        
        List<Restaurant> matchingRestaurants;
        
        if (dietaryPreferences == null || dietaryPreferences.isEmpty()) {
            matchingRestaurants = new ArrayList<>(mockRestaurants);
        } else {
            matchingRestaurants = mockRestaurants.stream()
                .filter(restaurant -> {
                    for (String option : restaurant.getDietaryOptions()) {
                        for (String preference : dietaryPreferences) {
                            if (option.toLowerCase().contains(preference.toLowerCase())) {
                                return true;
                            }
                        }
                    }
                    return false;
                })
                .collect(Collectors.toList());
        }
        
        if (matchingRestaurants.isEmpty()) {
            return null;
        }
        
        // Return a random restaurant from the matching ones
        int index = random.nextInt(matchingRestaurants.size());
        return matchingRestaurants.get(index);
    }
    
    /**
     * Gets the restaurant type from the place types.
     * 
     * @param place The place result
     * @return The restaurant type
     */
    private String getRestaurantType(PlacesSearchResult place) {
        if (place.types != null) {
            for (String type : place.types) {
                if (type.contains("cuisine")) {
                    return capitalize(type.replace("_", " "));
                }
            }
        }
        return "Restaurant";
    }
    
    /**
     * Gets the dietary options for a restaurant based on its types.
     * 
     * @param place The place result
     * @return Array of dietary options
     */
    private String[] getDietaryOptions(PlacesSearchResult place) {
        List<String> options = new ArrayList<>();
        
        if (place.types != null) {
            for (String type : place.types) {
                if (type.contains("vegetarian")) {
                    options.add("Végétarien");
                }
                if (type.contains("vegan")) {
                    options.add("Végétalien");
                }
            }
        }
        
        return options.toArray(new String[0]);
    }
    
    /**
     * Filters restaurants by dietary preferences.
     * 
     * @param restaurants List of restaurants
     * @param dietaryPreferences List of dietary preferences
     * @return Filtered list of restaurants
     */
    private List<Restaurant> filterByDietaryPreferences(List<Restaurant> restaurants, List<String> dietaryPreferences) {
        return restaurants.stream()
            .filter(restaurant -> {
                if (restaurant.getDietaryOptions() == null || restaurant.getDietaryOptions().length == 0) {
                    return false;
                }
                
                for (String option : restaurant.getDietaryOptions()) {
                    for (String preference : dietaryPreferences) {
                        if (option.toLowerCase().contains(preference.toLowerCase())) {
                            return true;
                        }
                    }
                }
                return false;
            })
            .collect(Collectors.toList());
    }
    
    /**
     * Calculates the distance between two points in kilometers.
     * 
     * @param point1 First point
     * @param point2 Second point
     * @return Distance in kilometers
     */
    private double calculateDistance(LatLng point1, LatLng point2) {
        // Earth's radius in kilometers
        final double R = 6371.0;
        
        // Convert latitude and longitude from degrees to radians
        double lat1 = Math.toRadians(point1.lat);
        double lon1 = Math.toRadians(point1.lng);
        double lat2 = Math.toRadians(point2.lat);
        double lon2 = Math.toRadians(point2.lng);
        
        // Haversine formula
        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;
        double a = Math.pow(Math.sin(dlat / 2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dlon / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        
        return R * c;
    }
    
    /**
     * Capitalizes the first letter of each word in a string.
     * 
     * @param text The text to capitalize
     * @return Capitalized text
     */
    private String capitalize(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }
        
        StringBuilder result = new StringBuilder();
        String[] words = text.split("\\s");
        for (String word : words) {
            if (!word.isEmpty()) {
                result.append(Character.toUpperCase(word.charAt(0)))
                      .append(word.substring(1).toLowerCase())
                      .append(" ");
            }
        }
        return result.toString().trim();
    }
    
    /**
     * Initializes a collection of mock restaurants.
     * 
     * @return List of mock restaurants
     */
    private List<Restaurant> initializeMockRestaurants() {
        List<Restaurant> restaurants = new ArrayList<>();
        
        restaurants.add(new Restaurant(
            "Le Petit Bistro",
            "123 Rue de Paris, 75001 Paris",
            "Français",
            4.5,
            1.2,
            new String[]{"Végétarien", "Sans Gluten"},
            "+33 1 23 45 67 89",
            "http://www.lepetitbistro.fr"
        ));
        
        restaurants.add(new Restaurant(
            "Sushi Sakura",
            "45 Avenue des Champs-Élysées, 75008 Paris",
            "Japonais",
            4.7,
            0.8,
            new String[]{"Végétarien", "Fruits de mer"},
            "+33 1 23 45 67 90",
            "http://www.sushisakura.fr"
        ));
        
        restaurants.add(new Restaurant(
            "Pasta Bella",
            "78 Boulevard Saint-Germain, 75006 Paris",
            "Italien",
            4.3,
            1.5,
            new String[]{"Végétarien", "Sans Lactose"},
            "+33 1 23 45 67 91",
            "http://www.pastabella.fr"
        ));
        
        restaurants.add(new Restaurant(
            "Le Végétalien",
            "12 Rue de Rivoli, 75004 Paris",
            "Végétalien",
            4.6,
            2.0,
            new String[]{"Végétarien", "Végétalien", "Sans Gluten", "Sans Lactose"},
            "+33 1 23 45 67 92",
            "http://www.levegetalien.fr"
        ));
        
        restaurants.add(new Restaurant(
            "Burger Gourmet",
            "34 Rue de la Paix, 75002 Paris",
            "Américain",
            4.2,
            1.1,
            new String[]{"Sans Gluten"},
            "+33 1 23 45 67 93",
            "http://www.burgergourmet.fr"
        ));
        
        restaurants.add(new Restaurant(
            "Taj Mahal",
            "56 Avenue Montaigne, 75008 Paris",
            "Indien",
            4.4,
            1.7,
            new String[]{"Végétarien", "Épicé"},
            "+33 1 23 45 67 94",
            "http://www.tajmahal.fr"
        ));
        
        restaurants.add(new Restaurant(
            "El Sombrero",
            "89 Rue Saint-Honoré, 75001 Paris",
            "Mexicain",
            4.1,
            2.2,
            new String[]{"Végétarien", "Épicé"},
            "+33 1 23 45 67 95",
            "http://www.elsombrero.fr"
        ));
        
        restaurants.add(new Restaurant(
            "Dragon d'Or",
            "67 Avenue de l'Opéra, 75002 Paris",
            "Chinois",
            4.0,
            1.3,
            new String[]{"Végétarien"},
            "+33 1 23 45 67 96",
            "http://www.dragondor.fr"
        ));
        
        restaurants.add(new Restaurant(
            "Le Couscous",
            "23 Boulevard Haussmann, 75009 Paris",
            "Marocain",
            4.8,
            1.9,
            new String[]{"Végétarien", "Sans Gluten"},
            "+33 1 23 45 67 97",
            "http://www.lecouscous.fr"
        ));
        
        restaurants.add(new Restaurant(
            "Greek Islands",
            "45 Rue de Vaugirard, 75015 Paris",
            "Grec",
            4.3,
            2.5,
            new String[]{"Végétarien", "Fruits de mer"},
            "+33 1 23 45 67 98",
            "http://www.greekislands.fr"
        ));
        
        return restaurants;
    }
}