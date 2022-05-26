package Model;

import Bomberman.Map;
import Bomberman.Window;
import Network.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Main {
        private static Client Client;
        private static Server Server;
        private static Thread serverThread;
        private static Thread clientThread;

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
                                clientThread = new Thread(Client);
                                clientThread.start();
                                Thread.sleep(200);
                                GameWindow.setMap(Game);

                        }catch (IOException e){

                        }
                }else{
                        Server = new Server(); //várakozik a csatlakozásig
                        serverThread = new Thread(Server);
                        serverThread.start();
                        Thread.sleep(200);
                        Server.sendMap(Game);
                        GameWindow.setMap(Game);
                }

                double lastTime = 0;

                while(Game.getLoser() == null)
                {
                        GeneralTimer = System.currentTimeMillis();
                        if((GeneralTimer-lastTime) / GameTick >= 1){
                                if(GameWindow.gameModeIsClient()) {
                                        Client.SendAction(Game);

                                        BufferedImage img = Client.getImage();
                                        Game.MapContent = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
                                        Graphics g = Game.MapContent.getGraphics();
                                        g.drawImage(img, 0, 0, null);
                                        g.dispose();

                                        GameWindow.repaint();   //grafikát frissíti
                                }else{
                                        //Game.Players.get(1).setAction(Server.getRemoteAction());
                                        Server.assertAction(Game);
                                        Game.Update();          //játékmechanikát frissíti
                                        Server.sendMap(Game);   //elküldi a játék állását a kliensnek
                                        GameWindow.repaint();   //grafikát frissíti
                                }
                                lastTime = GeneralTimer;
                                Thread.sleep(5);
                        }
                }
                System.out.println("Game over xxd");
        }
}