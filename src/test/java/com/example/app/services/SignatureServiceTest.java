package com.example.app.services;

import com.example.app.models.EmailSignature;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SignatureServiceTest {

    private final SignatureService signatureService = new SignatureService();

    @Test
    public void generateSignatureShouldCreateValidSignature() {
        String firstName = "John";
        String lastName = "Doe";
        String jobTitle = "Software Engineer";
        
        EmailSignature signature = signatureService.generateSignature(firstName, lastName, jobTitle);
        
        assertThat(signature).isNotNull();
        assertThat(signature.getFirstName()).isEqualTo(firstName);
        assertThat(signature.getLastName()).isEqualTo(lastName);
        assertThat(signature.getJobTitle()).isEqualTo(jobTitle);
        assertThat(signature.getSignature()).contains(firstName);
        assertThat(signature.getSignature()).contains(lastName);
        assertThat(signature.getSignature()).contains(jobTitle);
        assertThat(signature.getSignature()).contains("john.doe@company.com");
    }
}