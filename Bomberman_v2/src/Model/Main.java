package Model;

import Bomberman.Map;

public class Main {
        Double GeneralTime;
        Network.Client Client;
        Network.Server Server;

        //TODO: ezt nem tudom, hogy akarjuk csináni
        public static double GeneralTimer = System.currentTimeMillis();

        public static void main(String[] args) {

                Map Game = new Map();   //pálya létrehozása, generálása, megjelenítése

                double delta = 0;
                double lastTime = 0;

                while(true)
                {
                        GeneralTimer = System.currentTimeMillis();
                        delta += (GeneralTimer-lastTime) / 10;
                        lastTime = GeneralTimer;

                        if(delta >= 1){
                                Game.Update();          //játékmechanikát frissíti
                                Game.repaint();         //grafikát frissíti
                                delta=0;
                        }
                }
        }
}
