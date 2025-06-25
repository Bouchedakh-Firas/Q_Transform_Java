package com.example.app.controllers;

import com.example.app.models.EmailSignature;
import com.example.app.models.Joke;
import com.example.app.models.Message;
import com.example.app.services.JokeService;
import com.example.app.services.SignatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * REST controller for handling API requests.
 */
@RestController
@RequestMapping("/api")
public class ApiController {

    private final JokeService jokeService;
    private final SignatureService signatureService;

    @Autowired
    public ApiController(JokeService jokeService, SignatureService signatureService) {
        this.jokeService = jokeService;
        this.signatureService = signatureService;
    }

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
}