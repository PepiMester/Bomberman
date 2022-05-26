package Network;

import Bomberman.Map;
import Bomberman.Player;

import java.io.*;
import java.net.Socket;

public class Client {

    private Socket socket;
    private ObjectInputStream in;
    private DataOutputStream out;

    public Client(String IP) throws IOException {
        socket = new Socket(IP, 65535);
        in = new ObjectInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
    }

    public void SendAction(char action){
        try {
            out.write(action);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
    }

    public void Close(){
        try {
            in.close();
            //out.close();
            socket.close();
        }catch (IOException e){

        }
    }
}
