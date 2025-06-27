package com.example.app.controllers;

import com.example.app.models.EmailSignature;
import com.example.app.models.Joke;
import com.example.app.models.Message;
import com.example.app.models.Restaurant;
import com.example.app.services.JokeService;
import com.example.app.services.RestaurantService;
import com.example.app.services.SignatureService;
import com.google.maps.errors.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST controller for handling API requests.
 */
@RestController
@RequestMapping("/api")
public class ApiController {

    private static final Logger logger = LoggerFactory.getLogger(ApiController.class);
    private final JokeService jokeService;
    private final SignatureService signatureService;
    private final RestaurantService restaurantService;

    @Autowired
    public ApiController(JokeService jokeService, SignatureService signatureService, RestaurantService restaurantService) {
        this.jokeService = jokeService;
        this.signatureService = signatureService;
        this.restaurantService = restaurantService;
    }

    /**
     * API endpoint for testing the Google Places API.
     * Returns the first restaurant found near the specified address.
     * 
     * @param address The user's address
     * @param foodType Optional food type to filter restaurants (e.g., "italian", "japanese")
     * @return A Restaurant object or error details
     */
    @PostMapping("/restaurant/test")
    public ResponseEntity<?> testRestaurantApi(
            @RequestParam String address,
            @RequestParam(required = false) String foodType) {
        logger.info("Testing restaurant API with address: {} and food type: {}", address, foodType);
        
        try {
            Restaurant restaurant = restaurantService.testGooglePlacesApi(address, foodType);
            return ResponseEntity.ok(restaurant);
        } catch (ApiException e) {
            logger.error("Google API error: {}", e.getMessage(), e);
            Map<String, String> error = new HashMap<>();
            error.put("error", "Google API error: " + e.getMessage());
            // Using default status code 400 (BAD_REQUEST) as getHttpStatusCode() is not available in this version
            error.put("status", "400");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        } catch (IllegalStateException e) {
            logger.error("Restaurant search error: {}", e.getMessage(), e);
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (IOException e) {
            logger.error("I/O error: {}", e.getMessage(), e);
            Map<String, String> error = new HashMap<>();
            error.put("error", "Network or I/O error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        } catch (InterruptedException e) {
            logger.error("API call interrupted: {}", e.getMessage(), e);
            Thread.currentThread().interrupt();
            Map<String, String> error = new HashMap<>();
            error.put("error", "API call interrupted: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        } catch (Exception e) {
            logger.error("Unexpected error: {}", e.getMessage(), e);
            Map<String, String> error = new HashMap<>();
            error.put("error", "Unexpected error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    
    /**
     * API endpoint for the landing page.
     * @return A welcome message with the current timestamp.
     */
    @GetMapping("/welcome")
    public ResponseEntity<Message> getWelcomeMessage() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        Message message = new Message("Bienvenue sur l'application web Acloud Quarter!", timestamp);
        return ResponseEntity.ok(message);
    }
    
    /**
     * API endpoint for generating an email signature.
     * 
     * @param firstName The first name
     * @param lastName The last name
     * @param jobTitle The job title
     * @return An EmailSignature object with the generated signature
     */
    @PostMapping("/signature")
    public ResponseEntity<EmailSignature> createSignature(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String jobTitle) {
        
        EmailSignature signature = signatureService.generateSignature(firstName, lastName, jobTitle);
        return ResponseEntity.ok(signature);
    }
    
    /**
     * API endpoint for getting a random joke.
     * 
     * @return A Joke object containing a random joke
     */
    @GetMapping("/joke")
    public ResponseEntity<Joke> getRandomJoke() {
        Joke joke = jokeService.getRandomJoke();
        return ResponseEntity.ok(joke);
    }
    
    /**
     * API endpoint for finding a random restaurant based on address and dietary preferences.
     * 
     * @param address The user's address
     * @param dietaryPreferences List of dietary preferences
     * @param foodType Optional food type to filter restaurants (e.g., "italian", "japanese")
     * @return A Restaurant object containing information about a random restaurant
     */
    @PostMapping("/restaurant/random")
    public ResponseEntity<?> findRandomRestaurant(
            @RequestParam String address,
            @RequestParam(required = false) List<String> dietaryPreferences,
            @RequestParam(required = false) String foodType) {
        
        Restaurant restaurant = restaurantService.findRandomRestaurant(address, dietaryPreferences, foodType);
        
        if (restaurant == null) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(restaurant);
    }
}