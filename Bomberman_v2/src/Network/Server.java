package Network;

import Bomberman.Map;

import javax.imageio.ImageIO;
import java.awt.image.DataBufferByte;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable{

    private Socket socket;
    private ServerSocket s_socket;
    private DataInputStream in;
    private DataOutputStream out;
    private volatile char action;

    public Server(){
        try {
            s_socket = new ServerSocket(65535);
            socket = s_socket.accept();
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run(){
        while(true){
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
            /*
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(map.MapContent, "jpg", baos);
            byte[] bytes = baos.toByteArray();
            out.write(bytes);*/
            byte[] bytes = ((DataBufferByte) map.MapContent.getData().getDataBuffer()).getData();
            out.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
