package Network;

import Bomberman.Map;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;

public class Client implements Runnable {

    private Socket socket;
    private BufferedInputStream in;
    private DataOutputStream out;

    private BufferedImage kep = new BufferedImage(Map.getWidth() * 32, Map.getHeight() * 32, BufferedImage.TYPE_INT_RGB);
    public volatile int packetsize;

    public Client(String IP) throws IOException {
        socket = new Socket(IP, 65535);
        in = new BufferedInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
    }

    public void SendAction(Map map){
        try {
            out.write(map.Players.get(1).getAction());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    public Map ReceiveGameplay(){
        Map map = null;
        try {
            map = (Map) in.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void Close(){
        try {
            in.close();
            //out.close();
            socket.close();
        }catch (IOException e){

        }
    }*/

    public BufferedImage ReceiveGameplay(){
        return kep;
    }

    @Override
    public void run() {
        for(;;) {
            try {
                kep = ImageIO.read(in);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
