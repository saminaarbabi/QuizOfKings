package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;

public class GameGenerator {
    private Queue<String> players;
    private int rounds;
    private int roundTime;
    private int roundQuestionCount;
    private int standardTime;


    public void request(String userName , int round){
        players.add(userName);
        //thread needed
        startGame(userName , round);
    }

    public void startGame(String typeGame , int round){
        HashMap<String,Integer> userToScore = new HashMap<>();
        if (players.size()==2){
            userToScore.put(players.poll() , 0);
            userToScore.put(players.poll() , 0);
            for (int i = 0; i < rounds; i++) {
                Game game = new Game(userToScore,roundQuestionCount,roundTime , typeGame);
                //TODO
            }
        }
    }

    public String findType(String type){
        return type; //TODO
    }
}
