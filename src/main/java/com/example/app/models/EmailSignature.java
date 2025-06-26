package com.example.app.models;

/**
 * Model class representing an email signature.
 */
public class EmailSignature {
    private String firstName;
    private String lastName;
    private String jobTitle;
    private String signature;

    public EmailSignature() {
    }

    public EmailSignature(String firstName, String lastName, String jobTitle) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.jobTitle = jobTitle;
        this.generateSignature();
    }
    
    public EmailSignature(String firstName, String lastName, String jobTitle, String signature) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.jobTitle = jobTitle;
        this.signature = signature;
    }

    /**
     * Generates an email signature based on the provided information.
     */
    private void generateSignature() {
        StringBuilder sb = new StringBuilder();
        sb.append("Cordialement,\n\n");
        sb.append(firstName).append(" ").append(lastName).append("\n");
        sb.append(jobTitle).append("\n");
        sb.append("Acloud Quarter\n");
        sb.append("Email: ").append(firstName.toLowerCase()).append(".").append(lastName.toLowerCase()).append("@acloud.com\n");
        sb.append("Téléphone: +33 1 23 45 67 89");
        
        this.signature = sb.toString();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
    
    /**
     * Regenerates the signature after properties have been updated.
     */
    public void updateSignature() {
        generateSignature();
    }
}