package com.example.app.models;

/**
 * Model class representing a joke.
 */
public class Joke {
    private String content;
    private String category;

    public Joke() {
    }

    public Joke(String content, String category) {
        this.content = content;
        this.category = category;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}