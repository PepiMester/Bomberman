package Network;

import Bomberman.Map;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private Socket socket;
    private ServerSocket s_socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public Server(){
        try {
            s_socket = new ServerSocket(65535);
            socket = s_socket.accept();
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMap(Map map){
        try {
            out.writeObject(map); //TODO: ez az egy SOR valami mágia folyatán csak egyszer fut le WTF
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void assertAction(Map map){
        try {
            char action = in.readChar();    //várakozik adatra
            map.Players.get(1).setAction(action);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
