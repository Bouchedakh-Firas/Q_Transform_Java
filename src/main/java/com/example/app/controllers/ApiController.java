package com.example.app.controllers;

import com.example.app.models.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * REST controller for handling API requests.
 */
@RestController
@RequestMapping("/api")
public class ApiController {

    /**
     * API endpoint for the landing page.
     * @return A welcome message with the current timestamp.
     */
    @GetMapping("/welcome")
    public ResponseEntity<Message> getWelcomeMessage() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        Message message = new Message("Welcome to Java 8 Web Application!", timestamp);
        return ResponseEntity.ok(message);
    }
}