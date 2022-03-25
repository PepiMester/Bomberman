package hu.brszt.tcpdemo;

import java.io.*;
import java.net.*;

public class TCPClient {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public TCPClient(String host, int port){
        //új socket, és annak író- olvasói
        try {
            socket = new Socket(host, port);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            System.out.println("Server exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void SendText(String text){
        //írunk a socketre
        out.println(text);
    }

    public String ReadResponse(){
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
