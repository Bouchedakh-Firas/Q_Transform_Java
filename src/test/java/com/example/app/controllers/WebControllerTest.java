package com.example.app.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(WebController.class)
public class WebControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void indexShouldReturnIndexView() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    public void jokeShouldReturnJokeView() throws Exception {
        mockMvc.perform(get("/joke"))
                .andExpect(status().isOk())
                .andExpect(view().name("joke"));
    }

    @Test
    public void signatureShouldReturnSignatureView() throws Exception {
        mockMvc.perform(get("/signature"))
                .andExpect(status().isOk())
                .andExpect(view().name("signature"));
    }
    
    @Test
    public void restaurantShouldReturnRestaurantView() throws Exception {
        mockMvc.perform(get("/restaurant"))
                .andExpect(status().isOk())
                .andExpect(view().name("restaurant"));
    }
    
    @Test
    public void performanceShouldReturnPerformanceView() throws Exception {
        mockMvc.perform(get("/performance"))
                .andExpect(status().isOk())
                .andExpect(view().name("performance"));
    }
}