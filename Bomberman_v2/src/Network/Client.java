package Network;

import Bomberman.Map;
import Bomberman.Player;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;

import static Model.Main.Game;

public class Client implements Runnable{
    private static volatile BufferedImage kep;
    private Socket socket;
    private static DataInputStream in;
    private DataOutputStream out;

    public Client(String IP) throws IOException {
        socket = new Socket(IP, 65535);
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
    }

    public void SendAction(Player player){
        try {
            out.write(player.getAction());
            System.out.println((int)player.getAction());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static BufferedImage ReceiveGameplay(){
       return kep;
    }

    public void Close(){
        try {
            in.close();
            //out.close();
            socket.close();
        }catch (IOException e){

        }
    }

    @Override
    public void run() {
        for(;;) {
System.out.println("OBLITERATE!!");
            try {

                kep = ImageIO.read(in);

            } catch (IOException e) {
                e.printStackTrace();
            }
            throw new RuntimeException();
        }
    }
}
