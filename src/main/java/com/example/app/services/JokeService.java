package com.example.app.services;

import com.example.app.models.Joke;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Service class for generating random jokes in French.
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
     * Initializes a collection of jokes in French.
     * @return List of jokes
     */
    private List<Joke> initializeJokes() {
        List<Joke> jokeList = new ArrayList<>();
        
        jokeList.add(new Joke("Pourquoi les scientifiques ne font-ils pas confiance aux atomes ? Parce qu'ils inventent tout !", "Science"));
        jokeList.add(new Joke("Pourquoi le développeur Java portait-il des lunettes ? Parce qu'il ne pouvait pas C# !", "Programmation"));
        jokeList.add(new Joke("Combien de programmeurs faut-il pour changer une ampoule ? Aucun, c'est un problème matériel.", "Programmation"));
        jokeList.add(new Joke("Il y a 10 types de personnes dans le monde : ceux qui comprennent le binaire et ceux qui ne le comprennent pas.", "Programmation"));
        jokeList.add(new Joke("Pourquoi le livre de maths était-il triste ? Parce qu'il avait trop de problèmes.", "Mathématiques"));
        jokeList.add(new Joke("Comment appelle-t-on des pâtes fausses ? Des imposteurs !", "Nourriture"));
        jokeList.add(new Joke("Pourquoi les squelettes ne se battent-ils pas entre eux ? Ils n'ont pas le cran.", "Halloween"));
        jokeList.add(new Joke("J'ai dit à ma femme qu'elle dessinait ses sourcils trop hauts. Elle avait l'air surprise.", "Général"));
        jokeList.add(new Joke("Qu'est-ce qu'il y a de bien en Suisse ? Je ne sais pas, mais le drapeau est un grand plus.", "Géographie"));
        jokeList.add(new Joke("Je lis un livre sur l'anti-gravité. C'est impossible à poser !", "Science"));
        
        return jokeList;
    }
}