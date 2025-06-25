package com.example.app.services;

import com.example.app.models.Restaurant;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Service class for finding restaurants based on location and dietary preferences.
 * Currently uses mock data, but can be extended to use external APIs.
 */
@Service
public class RestaurantService {
    
    private final List<Restaurant> mockRestaurants;
    private final Random random;
    
    public RestaurantService() {
        this.mockRestaurants = initializeMockRestaurants();
        this.random = new Random();
    }
    
    /**
     * Finds a random restaurant near the specified address that matches the dietary preferences.
     * 
     * @param address The user's address
     * @param dietaryPreferences List of dietary preferences
     * @return A random restaurant that matches the criteria, or null if none found
     */
    public Restaurant findRandomRestaurant(String address, List<String> dietaryPreferences) {
        // In a real implementation, this would call an external API
        // For now, we'll filter our mock data based on dietary preferences
        
        List<Restaurant> matchingRestaurants;
        
        if (dietaryPreferences == null || dietaryPreferences.isEmpty()) {
            matchingRestaurants = new ArrayList<>(mockRestaurants);
        } else {
            matchingRestaurants = mockRestaurants.stream()
                .filter(restaurant -> {
                    for (String option : restaurant.getDietaryOptions()) {
                        if (dietaryPreferences.contains(option.toLowerCase())) {
                            return true;
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