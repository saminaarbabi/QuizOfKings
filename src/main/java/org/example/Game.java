package org.example;

import java.util.HashMap;
import java.util.Queue;

public class Game extends RoundGenerator{
    private Queue<String> players;

    public Game(HashMap<String, Integer> userToScore, String type, Queue<String> players) {
        super(userToScore,type);
        this.players = players;
    }

    public void startGame(String typeGame , int round){
        HashMap<String,Integer> userToScore = new HashMap<>();

        if (players.size()==2){
            userToScore.put(players.poll() , 0);
            userToScore.put(players.poll() , 0);

            for (int j = 0; j < 3 ; j++){
                DataPack type = new DataPack("type",Server.ConnectionHandler.in);
                String strType = (String) type.data ;

                for (int i = 0; i < 3; i++) {
                    Round game= new Round(userToScore);
                    game.roundControl(strType);
                    HashMap<String , Integer> tmp = game.getUserToScore();
                    for (String key : tmp.keySet()){
                        if(userToScore.containsKey(key)){
                            userToScore.put(key , userToScore.get(key) + tmp.get(key));
                        }
                        else
                            userToScore.put(key , tmp.get(key));
                    }
                }

            }



        }
    }

    public void request(String userName , int round){
        players.add(userName);
        //thread needed
        startGame(userName , round);
    }


}
