package Model;

import Bomberman.Map;
import Bomberman.Window;

public class Main {
        Double GeneralTime;
        Network.Client Client;
        Network.Server Server;

        public static double GeneralTimer = System.currentTimeMillis();

        public static void main(String[] args) throws InterruptedException {

                Map Game = new Map();   //pálya létrehozása, generálása, megjelenítése
                Window GameWindow = new Window("Bomberman", Game);

                GameWindow.gameStarted.await();
                double lastTime = 0;

                while(true)
                {
                        GeneralTimer = System.currentTimeMillis();
                        if((GeneralTimer-lastTime) / 20 >= 1){
                                Game.Update();          //játékmechanikát frissíti
                                GameWindow.repaint();         //grafikát frissíti
                                lastTime = GeneralTimer;
                        }
                }
        }
}
