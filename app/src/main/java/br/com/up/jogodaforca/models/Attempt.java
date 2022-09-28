package br.com.up.jogodaforca.models;

public class Attempt {
    private final String description;
    private final boolean correct;

    public Attempt(String description, boolean correct) {
        this.description = description;
        this.correct = correct;
    }

    public String getDescription() {
        return this.description;
    }

    public boolean getCorrect() {
        return this.correct;
    }
}