package Model;

import Bomberman.Map;
import Bomberman.Window;
import Network.*;

import java.io.IOException;

public class Main {
        private static Client Client;
        private static Server Server;
        private static Thread serverThread;

        //TODO külön szálon a kommunikációt

        private static long GeneralTimer = System.currentTimeMillis();
        private static final int GameTick = 20;

        public static void main(String[] args) throws InterruptedException {

                Window GameWindow = new Window("Bomberman");
                Map Game = new Map();   //pálya létrehozása, generálása

                //játékmód kiválasztása...
                GameWindow.gameModeSelected.await();
                Thread.sleep(100);
                GameWindow.repaint();

                if(GameWindow.gameModeIsClient()){
                        try {
                                Client = new Client(GameWindow.getHostAddress());
                        }catch (IOException e){

                        }
                }else{
                        Server = new Server(); //várakozik a csatlakozásig
                        serverThread = new Thread(Server);
                        serverThread.start();
                        Thread.sleep(200);
                        //Server.sendMap(Game);
                        GameWindow.setMap(Game);
                }

                double lastTime = 0;

                while(Game.getLoser() == null)
                {
                        GeneralTimer = System.currentTimeMillis();
                        if((GeneralTimer-lastTime) / GameTick >= 1){
                                if(GameWindow.gameModeIsClient()) {
                                        Client.SendAction(Map.Players.get(1).getAction());
                                        //Game = Client.ReceiveGameplay();
                                        //GameWindow.setMap(Game);
                                        //GameWindow.repaint();   //grafikát frissíti
                                }else{
                                        //Game.Players.get(1).setAction(Server.getRemoteAction());
                                        Server.assertAction(Game);
                                        Game.Update();          //játékmechanikát frissíti
                                        //Server.sendMap(Game);   //elküldi a játék állását a kliensnek
                                        GameWindow.repaint();   //grafikát frissíti
                                }
                                lastTime = GeneralTimer;
                                Thread.sleep(5);
                        }
                }
                System.out.println("Game over xxd");
        }
}