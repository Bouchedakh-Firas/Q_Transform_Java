package com.example.app.controllers;

import com.example.app.models.EmailSignature;
import com.example.app.models.JavaVersion;
import com.example.app.models.Joke;
import com.example.app.models.Message;
import com.example.app.models.Restaurant;
import com.example.app.services.JavaScriptService;
import com.example.app.services.JokeService;
import com.example.app.services.RestaurantService;
import com.example.app.services.SignatureService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ApiController.class)
public class ApiControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private JokeService jokeService;

        @MockBean
        private SignatureService signatureService;

        @MockBean
        private RestaurantService restaurantService;

        @MockBean
        private JavaScriptService javaScriptService;

        @Test
        public void welcomeMessageShouldReturnDefaultMessage() throws Exception {
                mockMvc.perform(get("/api/welcome"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.content")
                                                .value("Bienvenue sur l'application web Acloud Quarter!"))
                                .andExpect(jsonPath("$.timestamp").isNotEmpty());
        }

        @Test
        public void createSignatureShouldReturnValidSignature() throws Exception {
                // Given
                EmailSignature signature = new EmailSignature("Jane", "Smith", "Product Manager",
                                "Jane Smith\nProduct Manager\nAcloud Quarter\njane.smith@company.com\n+33 1 23 45 67 89");

                when(signatureService.generateSignature(eq("Jane"), eq("Smith"), eq("Product Manager")))
                                .thenReturn(signature);

                // When & Then
                mockMvc.perform(post("/api/signature")
                                .param("firstName", "Jane")
                                .param("lastName", "Smith")
                                .param("jobTitle", "Product Manager")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.firstName").value("Jane"))
                                .andExpect(jsonPath("$.lastName").value("Smith"))
                                .andExpect(jsonPath("$.jobTitle").value("Product Manager"))
                                .andExpect(jsonPath("$.signature").isNotEmpty());
        }

        @Test
        public void getRandomJokeShouldReturnJoke() throws Exception {
                // Given
                Joke joke = new Joke(
                                "Pourquoi les scientifiques ne font-ils pas confiance aux atomes ? Parce qu'ils inventent tout !",
                                "Science");

                when(jokeService.getRandomJoke()).thenReturn(joke);

                // When & Then
                mockMvc.perform(get("/api/joke"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.content").value(
                                                "Pourquoi les scientifiques ne font-ils pas confiance aux atomes ? Parce qu'ils inventent tout !"))
                                .andExpect(jsonPath("$.category").value("Science"));
        }

        @Test
        public void findRandomRestaurantShouldReturnRestaurant() throws Exception {
                // Given
                Restaurant restaurant = new Restaurant(
                                "Le Petit Bistro",
                                "123 Rue de Paris, 75001 Paris",
                                "Français",
                                4.5,
                                1.2,
                                new String[] { "Végétarien", "Sans Gluten" },
                                "+33 1 23 45 67 89",
                                "http://www.lepetitbistro.fr");

                when(restaurantService.findRandomRestaurant(eq("123 Test Street, Paris"), eq(Collections.emptyList()),
                                eq(null)))
                                .thenReturn(restaurant);

                // When & Then
                mockMvc.perform(post("/api/restaurant/random")
                                .param("address", "123 Test Street, Paris")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.name").value("Le Petit Bistro"))
                                .andExpect(jsonPath("$.address").value("123 Rue de Paris, 75001 Paris"))
                                .andExpect(jsonPath("$.cuisineType").value("Français"))
                                .andExpect(jsonPath("$.rating").value(4.5))
                                .andExpect(jsonPath("$.distance").value(1.2))
                                .andExpect(jsonPath("$.dietaryOptions[0]").value("Végétarien"))
                                .andExpect(jsonPath("$.dietaryOptions[1]").value("Sans Gluten"))
                                .andExpect(jsonPath("$.phoneNumber").value("+33 1 23 45 67 89"))
                                .andExpect(jsonPath("$.website").value("http://www.lepetitbistro.fr"));
        }

        @Test
        public void findRandomRestaurantWithPreferencesShouldReturnRestaurant() throws Exception {
                // Given
                Restaurant restaurant = new Restaurant(
                                "Le Végétalien",
                                "12 Rue de Rivoli, 75004 Paris",
                                "Végétalien",
                                4.6,
                                2.0,
                                new String[] { "Végétarien", "Végétalien", "Sans Gluten", "Sans Lactose" },
                                "+33 1 23 45 67 92",
                                "http://www.levegetalien.fr");

                when(restaurantService.findRandomRestaurant(eq("123 Test Street, Paris"),
                                eq(Collections.singletonList("végétarien")), eq(null)))
                                .thenReturn(restaurant);

                // When & Then
                mockMvc.perform(post("/api/restaurant/random")
                                .param("address", "123 Test Street, Paris")
                                .param("dietaryPreferences", "végétarien")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.name").value("Le Végétalien"))
                                .andExpect(jsonPath("$.dietaryOptions[0]").value("Végétarien"));
        }

        @Test
        public void testRestaurantApiShouldReturnRestaurant() throws Exception {
                // Given
                Restaurant restaurant = new Restaurant(
                                "Le Petit Bistro",
                                "123 Rue de Paris, 75001 Paris",
                                "Français",
                                4.5,
                                1.2,
                                new String[] { "Végétarien", "Sans Gluten" },
                                "+33 1 23 45 67 89",
                                "http://www.lepetitbistro.fr");

                when(restaurantService.testGooglePlacesApi(eq("123 Test Street, Paris"), eq(null)))
                                .thenReturn(restaurant);

                // When & Then
                mockMvc.perform(post("/api/restaurant/test")
                                .param("address", "123 Test Street, Paris")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.name").value("Le Petit Bistro"))
                                .andExpect(jsonPath("$.address").value("123 Rue de Paris, 75001 Paris"))
                                .andExpect(jsonPath("$.cuisineType").value("Français"))
                                .andExpect(jsonPath("$.rating").value(4.5))
                                .andExpect(jsonPath("$.distance").value(1.2))
                                .andExpect(jsonPath("$.dietaryOptions[0]").value("Végétarien"))
                                .andExpect(jsonPath("$.dietaryOptions[1]").value("Sans Gluten"))
                                .andExpect(jsonPath("$.phoneNumber").value("+33 1 23 45 67 89"))
                                .andExpect(jsonPath("$.website").value("http://www.lepetitbistro.fr"));
        }

        @Test
        public void testRestaurantApiShouldReturnErrorWhenApiCallFails() throws Exception {
                // Given
                when(restaurantService.testGooglePlacesApi(eq("Invalid Address"), eq(null)))
                                .thenThrow(new IllegalStateException("Could not geocode address: Invalid Address"));

                // When & Then
                mockMvc.perform(post("/api/restaurant/test")
                                .param("address", "Invalid Address")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                                .andExpect(status().isNotFound())
                                .andExpect(jsonPath("$.error").value("Could not geocode address: Invalid Address"));
        }

        @Test
        public void testRestaurantApiShouldReturnErrorWhenGoogleApiExceptionOccurs() throws Exception {
                // Given
                // Use a custom exception that extends ApiException for testing
                com.google.maps.errors.ApiException apiException = new com.google.maps.errors.ApiException(
                                "Google API error occurred") {
                        // Anonymous subclass to bypass protected constructor
                };

                when(restaurantService.testGooglePlacesApi(eq("Problem Address"), eq(null)))
                                .thenThrow(apiException);

                // When & Then
                mockMvc.perform(post("/api/restaurant/test")
                                .param("address", "Problem Address")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                                .andExpect(status().isBadRequest())
                                .andExpect(jsonPath("$.error").value("Google API error: Google API error occurred"))
                                .andExpect(jsonPath("$.status").value("400"));
        }

        @Test
        public void findRandomRestaurantShouldReturn404WhenNoRestaurantFound() throws Exception {
                // Given
                when(restaurantService.findRandomRestaurant(eq("Invalid Address"), any(), any()))
                                .thenReturn(null);

                // When & Then
                mockMvc.perform(post("/api/restaurant/random")
                                .param("address", "Invalid Address")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                                .andExpect(status().isNotFound());
        }

        @Test
        public void getJavaVersionShouldReturnJavaVersionInfo() throws Exception {
                // When & Then
                mockMvc.perform(get("/api/java-version"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.version").isNotEmpty())
                                .andExpect(jsonPath("$.vendor").isNotEmpty())
                                .andExpect(jsonPath("$.vmName").isNotEmpty())
                                .andExpect(jsonPath("$.vmVersion").isNotEmpty())
                                .andExpect(jsonPath("$.runtimeName").isNotEmpty());
        }

}