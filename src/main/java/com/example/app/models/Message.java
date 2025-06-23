package com.example.app.models;

/**
 * A simple model class representing a message.
 */
public class Message {
    private String content;
    private String timestamp;

    public Message() {
    }

    public Message(String content, String timestamp) {
        this.content = content;
        this.timestamp = timestamp;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}