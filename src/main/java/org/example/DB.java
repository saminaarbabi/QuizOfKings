package org.example;

import java.sql.*;

public class DB {
    Connection connection  ;
    PreparedStatement statement ;
    String url = "jdbc:mysql://localhost:3306/root";
    String username = "root";
    String password = "Saminaarb8301";
    private static  DB instance;
    private void setConnection(){
        try {
            DriverManager.getConnection("jdbc:mysql://localhost:3306/sonoo","root","root");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static DB getInstance() {
        if (instance == null) {
            synchronized (DB.class) {
                if (instance == null) {
                    instance = new DB();
                }
            }
        }
        return instance;
    }
    public boolean signup(String username, String password, String email, String phoneNumber) {
        setConnection();

        try {
            statement = connection.prepareStatement("INSERT INTO players (username,password,email,phonenumber) VALUES (?,?,?,?)");

            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, email);
            statement.setString(4, phoneNumber);

            int rowsInserted = statement.executeUpdate();

            // If at least one row is inserted, return true
            if (rowsInserted > 0) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public boolean signIn(String username, String password) {
        setConnection();

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT password FROM players WHERE username= ?");
            statement.setString(1, username);
            ResultSet set = statement.executeQuery();

            if (set.next()) {
                String dbPassword = set.getString("password");

                if (dbPassword.equals(password)) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public boolean updateInfo(String username, String email, String phoneNumber) {
        setConnection();

        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE players SET email = ?, phonenumber = ? WHERE username = ?");
            statement.setString(1, email);
            statement.setString(2, phoneNumber);
            statement.setString(3, username);

            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



}
