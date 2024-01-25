package org.example;

import java.util.HashMap;
import java.util.Queue;

public class RoundGenerator extends Round{
    private String type;

    public RoundGenerator(HashMap<String, Integer> userToScore,  String type) {
        super(userToScore);
        this.type = type;
    }


//TODO
    //Ask the type of game


    public String findType(String type){
        return type; //TODO
    }
}
