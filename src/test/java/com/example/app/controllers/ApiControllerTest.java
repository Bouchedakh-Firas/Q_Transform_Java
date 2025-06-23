package com.example.app.controllers;

import com.example.app.models.Message;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

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
}