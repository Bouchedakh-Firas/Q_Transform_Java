package com.example.app.services;

import com.example.app.models.EmailSignature;
import org.springframework.stereotype.Service;

/**
 * Service class for generating email signatures.
 */
@Service
public class SignatureService {
    
    /**
     * Generates an email signature based on the provided information.
     * 
     * @param firstName The first name
     * @param lastName The last name
     * @param jobTitle The job title
     * @return An EmailSignature object with the generated signature
     */
    public EmailSignature generateSignature(String firstName, String lastName, String jobTitle) {
        return new EmailSignature(firstName, lastName, jobTitle);
    }
}