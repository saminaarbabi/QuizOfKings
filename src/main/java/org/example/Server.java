package org.example;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable {
    private ArrayList<ConnectionHandler> connectionHandlers ;
    private ServerSocket serverSocket ;
    private boolean done ;
    private ExecutorService pool ;

    public Server() {
        connectionHandlers = new ArrayList<>() ;

        done = false ;
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(9999) ;
            pool = Executors.newCachedThreadPool() ;
            while (!done){
                Socket client = serverSocket.accept() ;
                ConnectionHandler connectionHandler = new ConnectionHandler(client);
                pool.execute(connectionHandler);

            }
        } catch (IOException e) {
            shutDown();
        }

    }
    public void  shutDown(){
        done = true ;
        if (!serverSocket.isClosed()){
            try {
                serverSocket.close();
            } catch (IOException e) {

            }
        }
    }


    class  ConnectionHandler implements Runnable{
        private Socket client ;
        private BufferedReader in ;
        private PrintWriter out ;

        public ConnectionHandler(Socket client) {
            this.client = client;
        }

        @Override
        public void run() {
            try {
                out = new PrintWriter(client.getOutputStream() , true) ;
                in = new BufferedReader(new InputStreamReader(client.getInputStream())) ;
                DB db = DB.getInstance() ;
                Gson gson = new Gson() ;
                String type = in.readLine();
                DataPack dataPack = gson.fromJson(in.readLine() , DataPack.class) ;
                if (dataPack.getType().equals("signup")){
                    UserControl userControl = gson.fromJson(in.readLine() , UserControl.class) ;
                    boolean response =db.signup(userControl.getUserName() , userControl.getPassword(), userControl.getEmail(), userControl.getPhoneNumber());
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }
    public static void main(String [] args){
        Server server = new Server() ;
        server.run();
    }
}

