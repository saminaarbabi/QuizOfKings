package org.example;

import com.google.gson.Gson;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable {
    private ServerSocket serverSocket;
    private boolean done;
    private ExecutorService pool;

    public Server() {
        ArrayList<ConnectionHandler> connectionHandlers = new ArrayList<>();

        done = false;
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(9999);
            pool = Executors.newCachedThreadPool();
            while (!done) {
                Socket client = serverSocket.accept();
                ConnectionHandler connectionHandler = new ConnectionHandler(client);
                pool.execute(connectionHandler);

            }
        } catch (IOException e) {
            shutDown();
        }

    }

    public void shutDown() {
        done = true;
        if (!serverSocket.isClosed()) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    static class ConnectionHandler implements Runnable {
        private final Socket client;

        public static ObjectOutputStream out;
        public static ObjectInputStream in;

        public ConnectionHandler(Socket client) {
            this.client = client;
        }

        @Override
        public void run() {
            try {
                out = new ObjectOutputStream(client.getOutputStream());
                in = new ObjectInputStream(client.getInputStream());
                DB db = DB.getInstance();
                DataPack input = (DataPack) in.readObject() ;
                if (input.getType().equals("signUp")) {
                    HashMap<String , String> inputs = (HashMap<String , String>) input.getData();
                    String responseSign = db.signup(inputs.get("userName"), inputs.get("password"), inputs.get("email"), inputs.get("phoneNumber"));
                    DataPack response = new DataPack("responseSignUp" , responseSign) ;
                    out.writeObject(response);
                } else if (input.getType().equals("deleteQuestion")) {
                    String inputs = (String) input.getData() ;
                    QuestionBank questionBank = new QuestionBank();
                    boolean responseDeleteQ = questionBank.deleteQuestion(inputs) ;
                    DataPack response = new DataPack("responseDeletQuestion" , responseDeleteQ) ;
                    out.writeObject(response);
                } else if (input.getType().equals("addQuestion")) {
                    HashMap<String , String> inputs = (HashMap<String, String>) input.getData() ;
                    QuestionBank questionBank = new QuestionBank();
                    String responseAddQ = questionBank.addQuestion(inputs.get("text"),inputs.get("answers1"),inputs.get("answers2"),inputs.get("answers3"),inputs.get("answers4"),inputs.get("correctAnswer"),inputs.get("type")) ;
                    DataPack response = new DataPack("responseAddQuestion" , responseAddQ) ;
                    out.writeObject(response);
                } else if (input.getType().equals("getQuestionToEdit")) {
                    String question = (String)input.getData();
                    QuestionBank questionBank = new QuestionBank();
                    Question question1 = questionBank.findQuestion(question);
                    if (question1 == null){
                        String responseEditQ = "NULL";
                        DataPack response = new DataPack("getQuestionToEdit",responseEditQ);
                    }
                    else {
                        HashMap<String, String> question2 = new HashMap<>();
                        question2.put("text", question1.getText());
                        question2.put("answers1", question1.getAnswers1());
                        question2.put("answers2", question1.getAnswers2());
                        question2.put("answers3", question1.getAnswers3());
                        question2.put("answers4", question1.getAnswers4());
                        question2.put("correctAnswer", question1.getCorrectAnswer());
                        question2.put("type", question1.getType());
                        DataPack response = new DataPack("responseAddQuestion", question2 );
                        out.writeObject(response);

                        DataPack editedQuestion = (DataPack) in.readObject();
                        if (editedQuestion.getType().equals("editedQuestion")){
                            HashMap<String , String> inputs = (HashMap<String, String>) input.getData() ;
                           Boolean editResponse = questionBank.updateCorrectAnswer(inputs.get("text") , inputs.get("answers1") , inputs.get("answers2") , inputs.get("answers3") , inputs.get("answers4") , inputs.get("correctAnswer") ,inputs.get("type")) ;
                           if (editResponse == true){
                               String responseEditQ = "success";
                               DataPack editedResponse = new DataPack("getQuestionToEdit",responseEditQ);
                           }
                           else {
                               String responseEditQ = "failed";
                               DataPack editedResponse = new DataPack("getQuestionToEdit",responseEditQ);

                           }



                        }

                    }


                } else if (input.getType().equals("signIn")) {
                    HashMap<String , String> inputs = (HashMap<String, String>) input.getData() ;
                    boolean responseLog = db.signIn(inputs.get("userName"), inputs.get("password")) ;
                    DataPack response = new DataPack("responseSignIn" , responseLog) ;
                    out.writeObject(response);
                } else if (input.getType().equals("startGame")) {
                    HashMap<String , String> inputs = (HashMap<String, String>) input.getData() ;
                    Game game = new Game();
                     game.request(inputs.get("userName"));

                } else if (input.getType().equals("editProfile")) {
                    String userName = (String) input.getData();
                    HashMap<String , String> user =  db.findUser(userName) ;
                    if (user == null){

                    }
                    else {
                       DataPack findUser = new DataPack("existingProfile" , user) ;
                       out.writeObject(findUser);
                       DataPack editedUser = (DataPack) in.readObject();

                       if (editedUser.getType().equals("updatedProfile")){
                           HashMap<String , String> updatedUser= (HashMap<String, String>) editedUser.getData() ;

                           Boolean updateUser =db.updateInfo(Integer.parseInt(updatedUser.get("userId")) , updatedUser.get("username") , updatedUser.get("password") , updatedUser.get("email") , updatedUser.get("phoneNumber")) ;
                           if (updateUser){
                               String response ="success" ;
                               DataPack dpResponse = new DataPack("responseEditProfile" , response) ;
                               out.writeObject(dpResponse);

                           }
                           else {
                               String response = "failed" ;
                               DataPack dpResponse = new DataPack("responseEditProfile" , response) ;
                               out.writeObject(dpResponse);
                           }

                       }


                    }



                }

            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.run();
    }
}

