package com.brszt.swingdemo;

import java.io.*;
import java.net.Socket;

public class Client {

    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public Client(String host, int port){
        //új socket, és annak író- olvasói
        try {
            socket = new Socket(host, port);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.out.println("Server exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public boolean SendMap(Map map) {
        //írunk a socketre
        boolean success = false;
        try {
            out.writeObject(map);
            success  = true;
        }catch(IOException e){

        }
        return success;
    }

    public String ReceiveAction(){
        //olvasunk a socketről
        String response = null;
        try {
            response = in.readLine();
        } catch (IOException e) {
            System.out.println("Server exception: " + e.getMessage());
            e.printStackTrace();
        }
        return response;
    }

    public void close(){
        //lezárunk mindent
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
