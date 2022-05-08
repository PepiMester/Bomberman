package Bomberman;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;
import java.util.HashMap;

public class Map {

    public ArrayList<Explosion> Explosions;
    public ArrayList<Player> Players;
    public ArrayList<Bomb> Bombs;
    public ArrayList<Powerup> Powerups;
    public ArrayList<Obstacle> Obstacles;
    public HashMap<String, BufferedImage> Sprites;

    public enum Images {
        Background,
        Unbreakable_Obstacle,
        Breakable_Obstacle,
        Powerup_Range,
        Powerup_Speed,
        Power_MaxBomb,
        Powerup_Pierce,
        Powerup_HP;

        public BufferedImage Sprite = null;
    }

    public void readSprites() {


        try {
            Sprites.put("UnbreakableObstacle", ImageIO.read(new File("/Sprites/obstacle.png")));
            //Graphics g = new Graphics();


        }

        catch (IOException e) {
            System.err.println("Nem sikerült beolvasni a képet");
        }


    }
    public Map() {

        //Sprite-ok beolvasása
        readSprites();

        //játékosok lerakása a pálya átelennes sarkaiba
        //TODO
        //Players.add(new Player(Sprites.get("Player1_sprites", [0,0])));
        //Players.add(new Player(Sprites.get("Player1_sprites", [0,0])));


        //15x15-ös pálya létrehozása
        for (int i=0; i<15; i++) {
            for (int j=0; j<15; j++) {

                //pálya körbe rakása felrobbanthatatlan akadályokkal
                if(i==0 || i==14 || j==0 || j==14) {
                    //Obstacles.add(new Obstacle())
                }

                //felrobbanthatatlan akadályok sakktábla szerúen előre megadott potícióban
                if {

                }

                //felrobbantható akadályok véletlenszerű esállyel, de a játékoshoz 2 mezőnél nem közelebb
                //TODO


            }
        }


    }

    public void Update () {

        BufferedImage img = Sprites.get("UnbreakableObstacle");
    }



}


