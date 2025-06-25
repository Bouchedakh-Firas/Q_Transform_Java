package com.example.app.controllers;

import com.example.app.models.EmailSignature;
import com.example.app.models.Joke;
import com.example.app.models.Message;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void welcomeMessageShouldReturnDefaultMessage() {
        ResponseEntity<Message> response = this.restTemplate.getForEntity(
                "http://localhost:" + port + "/api/welcome", Message.class);
        
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody().getContent()).isEqualTo("Welcome to Java 8 Web Application!");
        assertThat(response.getBody().getTimestamp()).isNotNull();
    }
    
    @Test
    public void createSignatureShouldReturnValidSignature() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("firstName", "Jane");
        map.add("lastName", "Smith");
        map.add("jobTitle", "Product Manager");
        
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        
        ResponseEntity<EmailSignature> response = this.restTemplate.postForEntity(
                "http://localhost:" + port + "/api/signature", request, EmailSignature.class);
        
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody().getFirstName()).isEqualTo("Jane");
        assertThat(response.getBody().getLastName()).isEqualTo("Smith");
        assertThat(response.getBody().getJobTitle()).isEqualTo("Product Manager");
        assertThat(response.getBody().getSignature()).contains("Jane Smith");
        assertThat(response.getBody().getSignature()).contains("Product Manager");
        assertThat(response.getBody().getSignature()).contains("jane.smith@company.com");
    }
    
    @Test
    public void getRandomJokeShouldReturnJoke() {
        ResponseEntity<Joke> response = this.restTemplate.getForEntity(
                "http://localhost:" + port + "/api/joke", Joke.class);
        
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody().getContent()).isNotEmpty();
        assertThat(response.getBody().getCategory()).isNotEmpty();
    }
}