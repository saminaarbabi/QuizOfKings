package org.example;

import java.util.ArrayList;

public class Question {
    private String text;
    private String answers1;
    private String answers2;
    private String answers3;
    private String answers4;
    private String correctAnswer;
    private String type;

    public Question( String text, String answers1, String answers2, String answers3, String answers4, String correctAnswer, String type) {
        this.text = text;
        this.answers1 = answers1;
        this.answers2 = answers2;
        this.answers3 = answers3;
        this.answers4 = answers4;
        this.correctAnswer = correctAnswer;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
//TODO
    // add question


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAnswers1() {
        return answers1;
    }

    public void setAnswers1(String answers1) {
        this.answers1 = answers1;
    }

    public String getAnswers2() {
        return answers2;
    }

    public void setAnswers2(String answers2) {
        this.answers2 = answers2;
    }

    public String getAnswers3() {
        return answers3;
    }

    public void setAnswers3(String answers3) {
        this.answers3 = answers3;
    }

    public String getAnswers4() {
        return answers4;
    }

    public void setAnswers4(String answers4) {
        this.answers4 = answers4;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}
