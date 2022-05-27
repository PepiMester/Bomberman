package Network;

import Bomberman.Map;

import javax.imageio.ImageIO;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable{

    private Socket socket;
    private ServerSocket s_socket;
    private DataInputStream in;
    private BufferedOutputStream out;
    private volatile char action;
    private volatile boolean running = true;

    public Server(){
        try {
            s_socket = new ServerSocket(65535);
            socket = s_socket.accept();
            in = new DataInputStream(socket.getInputStream());
            out = new BufferedOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run(){
        while(running){
            try {
                action = (char) in.read();    //várakozik adatra
            }catch (IOException ex){
                ex.printStackTrace();
            }
        }
    }

    public void assertAction(Map map){
        map.Players.get(1).setAction(action);
    }

    public void sendMap(Map map){
        try {
            ImageIO.write(map.MapContent, "bmp", out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop(){
        running = false;
    }

    public void Close() {
        try {
            out.close();
            in.close();
            socket.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
