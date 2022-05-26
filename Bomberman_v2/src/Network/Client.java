package Network;

import Bomberman.Map;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;

public class Client implements Runnable {

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    private BufferedImage image = new BufferedImage(Map.getWidth() * 32, Map.getHeight() * 32, BufferedImage.TYPE_INT_RGB);

    public Client(String IP) throws IOException {
        socket = new Socket(IP, 65535);
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
    }

    public void SendAction(Map map){
        try {
            out.write(map.Players.get(1).getAction());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run(){
        try {
            byte[] bytes = in.readAllBytes();
            InputStream is = new ByteArrayInputStream(bytes);
            image = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage getImage() {
        System.out.println(new Color(image.getRGB(48, 48)).getBlue());
        return image;
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
        return map;
    }*/

    public void Close(){
        try {
            in.close();
            //out.close();
            socket.close();
        }catch (IOException e){

        }
    }
}
