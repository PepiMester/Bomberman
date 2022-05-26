package Model;

import Bomberman.Map;
import Bomberman.Window;
import Bomberman.mapthread;
import Network.*;

import java.io.IOException;

public class Main {
        private static Client Client;
        private static Server Server;

        private static mapthread mapthread;
        private static Thread thread;

        private static Thread threead;
        private static Thread threadmap;

        private static boolean sendmap;
        //TODO külön szálon a kommunikációt
        public static Map Game;
        private static long GeneralTimer = System.currentTimeMillis();
        private static final int GameTick = 20;

        public static void main(String[] args) throws InterruptedException {

                Window GameWindow = new Window("Bomberman");
                Game = new Map();   //pálya létrehozása, generálása

                //játékmód kiválasztása...
                GameWindow.gameModeSelected.await();
                Thread.sleep(1);
                GameWindow.repaint();

                if(GameWindow.gameModeIsClient()){
                        try {
                                Client = new Client(GameWindow.getHostAddress());


                        }catch (IOException e){

                        }
                        GameWindow.setMap(Game);
                        threead = new Thread(Client);
                        threead.start();
                }else{
                        Server = new Server(); //várakozik a csatlakozásig
                        thread = new Thread(Server);
                        threadmap = new Thread(mapthread);

                        thread.start();
                        //Server.sendMap(Game);
                        threadmap.start();

                        GameWindow.setMap(Game);
                }

                double lastTime = 0;

                while(Game.getLoser() == null)
                {
                        GeneralTimer = System.currentTimeMillis();
                        if((GeneralTimer-lastTime) / GameTick >= 1){
                                if(GameWindow.gameModeIsClient()){

                                        Client.SendAction(Map.Players.get(1));

                                        Game.MapContent = Client.ReceiveGameplay();
                                        GameWindow.repaint();
                                        //Game.Rajzoljgec();
                                        //Game = Client.ReceiveGameplay();
                                        //GameWindow.setMap(Game);
                                        //GameWindow.repaint();   //grafikát frissíti
                                        lastTime = GeneralTimer;
                                }else{

                                        Server.assertAction(Game);
                                        Game.Update();
                                       // Game.Rajzoljgec();//játékmechanikát frissíti
                                           //elküldi a játék állását a kliensnek
                                        GameWindow.repaint();   //grafikát frissíti
                                        lastTime = GeneralTimer;
                                }
                        }
                }
                System.out.println("Game over xxd");
        }
}
