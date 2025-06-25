package com.example.app.services;

import com.example.app.models.Joke;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JokeServiceTest {

    private final JokeService jokeService = new JokeService();

    @Test
    public void getRandomJokeShouldReturnJoke() {
        Joke joke = jokeService.getRandomJoke();
        
        assertThat(joke).isNotNull();
        assertThat(joke.getContent()).isNotEmpty();
        assertThat(joke.getCategory()).isNotEmpty();
    }
}