package com.example.app.services;

import com.example.app.models.Joke;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Service class for generating random jokes.
 */
@Service
public class JokeService {
    
    private final List<Joke> jokes;
    private final Random random;
    
    public JokeService() {
        this.jokes = initializeJokes();
        this.random = new Random();
    }
    
    /**
     * Returns a random joke from the collection.
     * @return A random joke
     */
    public Joke getRandomJoke() {
        int index = random.nextInt(jokes.size());
        return jokes.get(index);
    }
    
    /**
     * Initializes a collection of jokes.
     * @return List of jokes
     */
    private List<Joke> initializeJokes() {
        List<Joke> jokeList = new ArrayList<>();
        
        jokeList.add(new Joke("Why don't scientists trust atoms? Because they make up everything!", "Science"));
        jokeList.add(new Joke("Why did the Java developer wear glasses? Because they couldn't C#!", "Programming"));
        jokeList.add(new Joke("How many programmers does it take to change a light bulb? None, that's a hardware problem.", "Programming"));
        jokeList.add(new Joke("There are 10 types of people in the world: those who understand binary and those who don't.", "Programming"));
        jokeList.add(new Joke("Why was the math book sad? Because it had too many problems.", "Math"));
        jokeList.add(new Joke("What do you call a fake noodle? An impasta!", "Food"));
        jokeList.add(new Joke("Why don't skeletons fight each other? They don't have the guts.", "Halloween"));
        jokeList.add(new Joke("I told my wife she was drawing her eyebrows too high. She looked surprised.", "General"));
        jokeList.add(new Joke("What's the best thing about Switzerland? I don't know, but the flag is a big plus.", "Geography"));
        jokeList.add(new Joke("I'm reading a book on anti-gravity. It's impossible to put down!", "Science"));
        
        return jokeList;
    }
}