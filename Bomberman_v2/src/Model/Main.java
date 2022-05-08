package Model;
import javax.swing.*;
import java.awt.*;

import Bomberman.Map;

import java.time.Clock;

public class Main {
        Double GeneralTime;
        Map Map = new Map();
        Network.Client Client;
        Network.Server Server;

        public static void main(String[] args) {

                //A játék egyetlen ablakának létrehozása
                //System.out.println("HELLOBELLO");
                JFrame gw = new JFrame("Bomberman");
                gw.setVisible(true);
                gw.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);     //így lehet bezárni az ablakot X-szel
                gw.setSize(15*32,15*32);


        }
}
