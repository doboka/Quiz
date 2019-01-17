package com.example.android.quiz;

public class Answer {

    private String text;
    private boolean correct;

    public Answer(String text) {
        this.text = text;
    }

    public Answer(String text, boolean correct) {
        this.correct = correct;
        this.text = text;
    }

    public boolean isCorrect() {
        return correct;
    }

    public String getText() {
        return text;
    }
}
