package org.example;

import java.io.IOException;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Round implements Serializable{

    public Round() {
        this.userToScore =null;
    }

    Connection connection;
    Statement statement;
    PreparedStatement preparedStatement;
    ResultSet resultSet;
    private HashMap<String, Integer> userToScore;



    public HashMap<String, Integer> getUserToScore() {
        return userToScore;
    }

    public void setUserToScore(HashMap<String, Integer> userToScore) {
        this.userToScore = userToScore;
    }



    public void setConnection() {
        try {
            DriverManager.getConnection("jdbc:mysql://localhost:3306/quizofkings", "root", "Saminaarb8301");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Question randomQuestion(String typeUser) {
        int id = Integer.parseInt(null);
        String text = null;
        String answer1 = null;
        String answer2 = null;
        String answer3 = null;
        String answer4 = null;
        String correctAnswer = null;
        String type = null;
        try {
            setConnection();
            String sql = "SELECT * FROM questions WHERE type = ? ORDER BY RAND() LIMIT 1";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, typeUser);
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                text = resultSet.getString("question");
                answer1 = resultSet.getString("answer1");
                answer2 = resultSet.getString("answer2");
                answer3 = resultSet.getString("answer3");
                answer4 = resultSet.getString("answer4");
                correctAnswer = resultSet.getString("correctanswer");
                type = resultSet.getString("type");
            }


            connection.close();
            statement.close();
            resultSet.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return new Question(text, answer1, answer2, answer3, answer4, correctAnswer, type);
    }

    public void calculateScore(String userAnswer, String question, String userId) {
        try {

            String sqlQuery = "SELECT correctanswer FROM questions WHERE question = ?";
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, question);

            resultSet = preparedStatement.executeQuery();


            // Check if there is a match
            if (resultSet.next()) {
                String correctAnswer = resultSet.getString("correctanswer");
                if (userAnswer.equals(correctAnswer)) {
                    userToScore.put(userId, userToScore.get(userId) + 1);
                    //TODO
                    //Not sure
                }
            } else {
                //TODO
                // Handle the case when the question is not found
            }

        } catch (SQLException e) {

        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void roundControl(String type) {

        try {
            Question question = randomQuestion(type);
            DataPack dataPack = new DataPack("question", question);
            Server.ConnectionHandler.out.writeObject(dataPack);
            DataPack answer = new DataPack("answer",Server.ConnectionHandler.in);
            String strAnswer = (String) answer.data ;
            //TODO
            //NOT SURE
            DataPack userID = new DataPack("userID",Server.ConnectionHandler.in);
            String strID = (String)  userID.data;
            calculateScore(strAnswer,question.getText(),strID);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }


    //TODO
    //Calculate the score
    //Find the winner

}
