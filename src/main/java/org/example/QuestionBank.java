package org.example;

import java.sql.*;

public class QuestionBank {
    Connection connection;
    Statement statement;
    PreparedStatement preparedStatement;
    ResultSet resultSet;

    public void setConnection() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/quizofkings", "root", "Saminaarb8301");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


        public boolean deleteQuestion(String question) {
            setConnection();

            String deleteQuery = "DELETE FROM questions WHERE idQuestion = ?";

            try {
                preparedStatement = connection.prepareStatement(deleteQuery);
                // Set the parameter in the PreparedStatement
                preparedStatement.setString(1, question);

                // Execute the delete query
                int affectedRows = preparedStatement.executeUpdate();

                // Check the number of affected rows to determine if the deletion was successful
                return affectedRows > 0;

            } catch (SQLException e) {
                e.printStackTrace();
            }

            return false;
        }

        public Question findQuestion(String question){
        setConnection();

            String selectQuery = "SELECT question FROM questions WHERE question = ?";
            try {
                preparedStatement = connection.prepareStatement(selectQuery);
                preparedStatement.setString(1, question);

                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    Question question1= new Question(question,resultSet.getString("answer1"),resultSet.getString("answer2"),resultSet.getString("answer3"),resultSet.getString("answer4"),resultSet.getString("correctanswer"), resultSet.getString("type"));
                    return question1;
                }

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                // Close the connection and statement in the finally block
                try {
                    if (preparedStatement != null) {
                        preparedStatement.close();
                    }
                    if (connection != null) {
                        connection.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return null ;

        }


    public boolean updateCorrectAnswer(String question,String answer1,String answer2,String answer3,String answer4, String newCorrectAnswer,String type) {
        setConnection();
        // Specify the SQL query to update the correct answer
        String updateQuery = "UPDATE questions SET answer1 = ?, answer2 = ?, answer3 = ?, answer4 = ?, correctanswer = ?, type = ? WHERE question = ?";

        try {
            preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setString(1, answer1);
            preparedStatement.setString(2, answer2);
            preparedStatement.setString(3, answer3);
            preparedStatement.setString(4, answer4);
            preparedStatement.setString(5, newCorrectAnswer);
            preparedStatement.setString(6, type);
            preparedStatement.setString(7, question);

            int affectedRows = preparedStatement.executeUpdate();

            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public String addQuestion(String question, String answer1, String answer2, String answer3, String answer4, String correctAnswer ,String type) {
        setConnection();

        String insertQuery = "INSERT INTO questions (question, answer1, answer2, answer3, answer4 , correctanswer, type) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, question);
            preparedStatement.setString(2, answer1);
            preparedStatement.setString(3, answer2);
            preparedStatement.setString(4, answer3);
            preparedStatement.setString(5, answer4);
            preparedStatement.setString(6, correctAnswer);
            preparedStatement.setString(7, type);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0){
                return "success";
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "error";
    }


    }






