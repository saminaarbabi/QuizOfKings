package org.example;

public class UserControl {
    private String userName;
    private String password;
    private String email;
    private String phoneNumber;

    public boolean signUp(String userName, String password, String email, String phoneNumber){
        this.userName = userName ;
        this.password = password ;
        this.email = email ;
        this.phoneNumber = phoneNumber ;
        return true ;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
