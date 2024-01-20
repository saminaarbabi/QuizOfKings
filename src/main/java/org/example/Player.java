package org.example;

import java.util.ArrayList;

public class Player {
    private String username;
    private String password;
    private String profile;
    private String email;
    private String phoneNumber;
    private ArrayList <String> friends = null;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<String> getFriends() {
        return friends;
    }
    //TODO
    //add friends
    //remove friends


    public Player(String name, String profile, String email, String phoneNumber) {
        this.username = name;
        this.profile = profile;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
