package org.example;

import java.util.HashMap;
import java.util.Queue;

public class Game {
    private Queue<String> players;

    public Game() {
        this.players = null;
    }

    public void startGame(){

        HashMap<String,Integer> userToScore = new HashMap<>();

        if (players.size()==2){
            userToScore.put(players.poll() , 0);
            userToScore.put(players.poll() , 0);

            for (int j = 0; j < 3 ; j++){
                DataPack type = new DataPack("type",Server.ConnectionHandler.in);
                String strType = (String) type.data ;

                for (int i = 0; i < 3; i++) {
                    Round game= new Round();
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

    public void request(String userName){
        players.add(userName);
        //thread needed
        startGame();
        //TODO
        //return the components user id to client
    }


}
