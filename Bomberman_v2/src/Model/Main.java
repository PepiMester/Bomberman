package Model;

import Bomberman.Map;
import Bomberman.Window;
import Network.*;

import java.awt.*;

public class Main {
        private static Client Client;
        private static Server Server;
        private static Thread serverThread;
        private static Thread mapThread;

        private static long GeneralTimer;
        private static final int GameTick = 20;                         //játék állapotának frissítési gyakorisága ms-ban

        public static void main(String[] args) throws InterruptedException {

                Window GameWindow = new Window("Bomberman");       //ablak létrehozása
                Map Game = new Map();                                   //pálya létrehozása, generálása

                GameWindow.gameModeSelected.await();                    //várakozás a játékmód kiválasztásáig
                Thread.sleep(200);
                GameWindow.repaint();

                if(GameWindow.gameModeIsClient()){
                        Client = new Client(GameWindow.getHostAddress()); //csatlakozik a szerverhez
                        mapThread = new Thread(Client);                 //kliens indul
                        mapThread.start();
                }else{
                        Server = new Server();                          //várakozik a kliens csatlakozásáig
                        serverThread = new Thread(Server);              //szerver indul
                        serverThread.start();
                }
                Thread.sleep(200);
                GameWindow.setMap(Game);

                double lastTime = 0;

                while(Game.getLoser() == null)
                {
                        GeneralTimer = System.currentTimeMillis();
                        if((GeneralTimer-lastTime) / GameTick >= 1){    //eljött az idő a frissítésre
                                if(GameWindow.gameModeIsClient()) {
                                        Client.SendAction(Game);        //elküldi az akcióit
                                        Client.ReceiveGameplay(Game);   //frissíti a rendert
                                }else{
                                        Server.assertAction(Game);      //ellenfél akcióit feldolgozza
                                        Game.Update();                  //játékmechanikát frissíti
                                        Server.sendMap(Game);           //elküldi a játék állását a kliensnek
                                }
                                GameWindow.repaint();                   //frissíti az ablak tartalmát
                                lastTime = GeneralTimer;
                                Thread.sleep(5);                  //valamennyi CPU idő felszabadítása
                        }
                }

                if(!GameWindow.gameModeIsClient()){
                        if(Game.getLoser().equals(Game.Players.get(0))){
                                Game.drawGameOverScreen(true);
                                Server.sendMap(Game);
                                Game.drawGameOverScreen(false);
                                GameWindow.repaint();
                        }else{
                                Game.drawGameOverScreen(false);
                                Server.sendMap(Game);
                                Game.drawGameOverScreen(true);
                                GameWindow.repaint();
                        }
                }
        }
}