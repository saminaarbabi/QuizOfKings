package org.example;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import java.sql.*;

public class DB {
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    Connection connection  ;
    PreparedStatement statement ;
    String url = "jdbc:mysql://localhost:3306/quizofkings";
    String username = "root";
    String password = "Saminaarb8301";
    private static  DB instance;
    private void setConnection(){
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/quizofkings","root","Saminaarb8301");
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
    private boolean checkUserName(String username){
        setConnection();
        try {
            statement = connection.prepareStatement("SELECT username from players ") ;
            ResultSet set = statement.executeQuery();
            while (set.next()){
                String dbUserName = set.getString("username") ;
                if (username.equals(dbUserName)) return false ;
            }
            return true ;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

        public static boolean isValidEmail(String email) {
            Matcher matcher = pattern.matcher(email);
            return matcher.matches();
        }

         public static boolean isValidNumber(String input) {
        try {
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    public String signup(String username, String password, String email, String phoneNumber) {
        setConnection();

        try {
            if (!checkUserName(username)){
                return "duplicateUserName" ;
            }
            else if (!isValidEmail(email)) {
                return "invalidEmail";
            }
            else if (!isValidNumber(phoneNumber)) {
                return "invalidNumber";
            } else {
                statement = connection.prepareStatement("INSERT INTO players (username,password,email,phonenumber) VALUES (?,?,?,?)");

                statement.setString(1, username);
                statement.setString(2, password);
                statement.setString(3, email);
                statement.setString(4, phoneNumber);

                int rowsInserted = statement.executeUpdate();

                // If at least one row is inserted, return true
                if (rowsInserted > 0) {
                    return "success";
                } else {
                    return "fail";
                }
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


    public boolean updateInfo(int userId , String username,String password , String email, String phoneNumber) {
        setConnection();

        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE players SET email = ?, phonenumber = ? , username = ? , password = ?, WHERE userId = ?");
            statement.setString(1, email);
            statement.setString(2, phoneNumber);
            statement.setString(3, username);
            statement.setString(4, password);
            statement.setInt(5, userId);

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

    public HashMap<String ,String> findUser(String username){
        setConnection();
        HashMap<String , String> user = new HashMap<>() ;
        String query = "SELECT player FROM players WHERE username = ?";
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                user.put("userId" , String.valueOf(resultSet.getInt("userId"))) ;
                user.put("username" , resultSet.getString("username")) ;
                user.put("password" , resultSet.getString("password")) ;
                user.put("email" , resultSet.getString("email")) ;
                user.put("phonenumber" , resultSet.getString("phonenumber")) ;
                return user ;

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null ;


    }



}
