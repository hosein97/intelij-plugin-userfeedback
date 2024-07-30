package com.jetbrains.research.demoPlugin.model;

/**
 * Represents user feedback.
 */
public class UserFeedback {
    private final String feedback;
    private final String animalType;
    private final int rating;
 
    public UserFeedback(String feedback, String animalType, int rating) {
        this.feedback = feedback;
        this.animalType = animalType;
        this.rating = rating;
    }

    public String getFeedback() {
        return feedback;
    }

    public String getAnimalType() {
        return animalType;
    }

    public int getRating() {
        return rating;
    }
}
