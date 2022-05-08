package Bomberman;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.util.HashMap;

public class Map extends JFrame {

    public ArrayList<Explosion> Explosions;
    public ArrayList<Player> Players = new ArrayList<Player>();
    public ArrayList<Bomb> Bombs = new ArrayList<Bomb>();
    public ArrayList<Powerup> Powerups = new ArrayList<Powerup>();
    public ArrayList<Obstacle> Obstacles = new ArrayList<Obstacle>();
    public HashMap<String, BufferedImage> Sprites = new HashMap<String, BufferedImage>();

    //konstansok, hogy itt egyszerre lehessen paraméterezni a mapot, ne kelljen mindenhol átírni
    // ha változtatni akarunk esetleg rajta
    private final int map_width = 15;
    private final int map_height = 15;

    public void readSprites() {

        try {
            Sprites.put("UnbreakableObstacle", ImageIO.read(new File("./src/Sprites/wall.png")));
            Sprites.put("BreakableObstacle", ImageIO.read(new File("./src/Sprites/obstacle.png")));
            Sprites.put("Player1_sprites", ImageIO.read(new File("./src/Sprites/player1.png")));
            Sprites.put("Player2_sprites", ImageIO.read(new File("./src/Sprites/player2.png")));
        }
        catch (IOException e) {
            System.err.println("Nem sikerült beolvasni a képet");
        }
    }
    public Map() {

        //JFrame ősosztály inicializálása
        super("Bomberman");

        //Sprite-ok beolvasása
        readSprites();

        //játékosok lerakása a pálya átelennes sarkaiba
        Players.add(new Player(Sprites.get("Player1_sprites"), new int[] {1, 1}));
        Players.add(new Player(Sprites.get("Player2_sprites"), new int[] {map_width-2,  map_width-2}));

        //pálya létrehozása
        for (int i=0; i<map_width; i++) {
            for (int j=0; j<map_height; j++) {

                int[] position = new int[] {i, j};

                //pálya körbe rakása felrobbanthatatlan akadályokkal
                if(i==0 || i==map_width-1 || j==0 || j==map_height-1) {
                    Obstacles.add(new Obstacle(Sprites.get("UnbreakableObstacle"), position, false));
                }
                //felrobbanthatatlan akadályok sakktábla szerúen előre megadott potícióban
                else if((i%2==0) && (j%2==0))
                {
                    Obstacles.add(new Obstacle(Sprites.get("UnbreakableObstacle"), position, false));
                }
                //felrobbantható akadályok véletlenszerű eséllyel, de a játékoshoz 2 mezőnél nem közelebb
                else if(!((i==1 && j<4) || (i<4 && j==1) || (i==map_width-2 && j>map_height-5) || (i>map_width-5 && j==map_height-2))){
                    Obstacles.add(new Obstacle(Sprites.get("BreakableObstacle"), position, true));
                }
            }
        }

        //A játék egyetlen ablakának létrehozása
        setDefaultCloseOperation(EXIT_ON_CLOSE);     //így lehet bezárni az ablakot X-szel
        getContentPane().setPreferredSize(new Dimension(map_width * 32 + getInsets().left + getInsets().right, map_height * 32 + getInsets().top + getInsets().bottom));
        setResizable(false);
        pack();
        setVisible(true);
    }

    public void Update () {

        BufferedImage img = Sprites.get("UnbreakableObstacle");
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);
        g.setColor(Color.gray);
        g.fillRect(0, 0, map_width*32, map_height*32);
        for (Obstacle obstacle: Obstacles) {
            g.drawImage(obstacle.currentSprite, obstacle.position[0]*32+getInsets().left, obstacle.position[1]*32+getInsets().top, 32, 32, null);
        }

        for (Player player: Players) {
            g.drawImage(player.currentSprite, player.position[0]*32+getInsets().left, player.position[1]*32 + getInsets().top, null);
        }
    }
}


