package Network;

import Bomberman.Map;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import Bomberman.SynchronizedInputStream;
import Model.Main;
import com.sun.source.tree.SynchronizedTree;

import javax.imageio.ImageIO;

import static java.lang.Thread.sleep;

public class Server implements Runnable{

    private Socket socket;
    private ServerSocket s_socket;
    private static DataOutputStream out;
    private DataInputStream in;

    volatile char action = 4;
    public Server() {
        try {
            s_socket = new ServerSocket(65535);
            socket = s_socket.accept();
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendMap(Map map){
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(map.MapContent, "jpg", baos);
            byte[] bytes = baos.toByteArray();
            out.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


        public void run() {
        for(;;){
            try {
                action = (char) in.read();//in.readChar();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        }

    public void assertAction(Map map)  {


             //várakozik adatra
            map.Players.get(1).setAction(action);
            System.out.println((int)action);

    }


}
