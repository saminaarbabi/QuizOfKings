package org.example;

import java.util.ArrayList;
import java.util.HashMap;

public class Game {
    private ArrayList<Question> questions;
    private HashMap<String,Integer> userToScore;
    private int numberOfQuestions;
    private int time;
    private String gameType;


    public Game(HashMap<String, Integer> userToScore, int numberOfQuestions, int time, String gameType) {
        this.userToScore = userToScore;
        this.numberOfQuestions = numberOfQuestions;
        this.time = time;
        this.gameType = gameType;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public HashMap<String, Integer> getUserToScore() {
        return userToScore;
    }

    public void setUserToScore(HashMap<String, Integer> userToScore) {
        this.userToScore = userToScore;
    }

    public int getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public void setNumberOfQuestions(int numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    //TODO
    //random generator for q

}
