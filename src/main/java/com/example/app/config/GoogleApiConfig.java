package com.example.app.config;

import com.google.maps.GeoApiContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for Google API.
 */
@Configuration
public class GoogleApiConfig {

    @Value("${google.api.key}")
    private String apiKey;

    /**
     * Creates a GeoApiContext bean with the configured API key.
     * 
     * @return GeoApiContext instance
     */
    @Bean
    public GeoApiContext geoApiContext() {
        return new GeoApiContext.Builder()
                .apiKey(apiKey)
                .build();
    }
}