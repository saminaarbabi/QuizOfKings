package org.example;

import java.util.ArrayList;

public class Question {
    private String text;
    private ArrayList<String> answers;
    private String rightAnswers;
    private String type;

    public Question(String text, ArrayList<String> answers, String rightAnswers,String type) {
        this.text = text;
        this.answers = answers;
        this.rightAnswers = rightAnswers;
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

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<String> answers) {
        this.answers = answers;
    }

    public String getRightAnswers() {
        return rightAnswers;
    }

    public void setRightAnswers(String rightAnswers) {
        this.rightAnswers = rightAnswers;
    }
}
