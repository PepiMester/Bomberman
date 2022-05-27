package Network;

import Bomberman.Map;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;

public class Client implements Runnable {

    private Socket socket;
    private BufferedInputStream in;
    private DataOutputStream out;
    private BufferedImage kep;
    private volatile boolean running = true;

    public Client(String IP) {
        try{
            socket = new Socket(IP, 65535);
            in = new BufferedInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void SendAction(Map map){
        try {
            out.write(map.Players.get(1).getAction());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ReceiveGameplay(Map map){
        map.MapContent = kep;
    }

    @Override
    public void run() {
        while(running) {
            try {
                kep = ImageIO.read(in);
            } catch (IOException e) {
                e.printStackTrace();
            }
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
