package Bomberman;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.util.HashMap;

public class Map extends JFrame{

    public static ArrayList<Explosion> Explosions = new ArrayList<Explosion>();
    public static ArrayList<Player> Players = new ArrayList<Player>();
    public static ArrayList<Bomb> Bombs = new ArrayList<Bomb>();
    public static ArrayList<Powerup> Powerups = new ArrayList<Powerup>();
    public static ArrayList<Obstacle> Obstacles = new ArrayList<Obstacle>();
    public static HashMap<String, BufferedImage> Sprites = new HashMap<String, BufferedImage>();

    //konstansok, hogy itt egyszerre lehessen paraméterezni a mapot, ne kelljen mindenhol átírni
    // ha változtatni akarunk esetleg rajta
    private final int map_width = 15;
    private final int map_height = 15;

    private void readSprites() {

        try {
            Sprites.put("UnbreakableObstacle", ImageIO.read(new File("./src/Sprites/wall.png")));
            Sprites.put("BreakableObstacle", ImageIO.read(new File("./src/Sprites/obstacle.png")));
            Sprites.put("Player1_sprites", ImageIO.read(new File("./src/Sprites/player1.png")));
            Sprites.put("Player2_sprites", ImageIO.read(new File("./src/Sprites/player2.png")));
            Sprites.put("Powerup_HealthBoost", ImageIO.read(new File("./src/Sprites/healthboost.png")));
            Sprites.put("Powerup_ExtraAmmo", ImageIO.read(new File("./src/Sprites/extra_bomb.png")));
            Sprites.put("Powerup_Pierce", ImageIO.read(new File("./src/Sprites/pierce.png")));
            Sprites.put("Powerup_Speed", ImageIO.read(new File("./src/Sprites/speed.png")));
            Sprites.put("Powerup_Range", ImageIO.read(new File("./src/Sprites/range.png")));
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
        Players.add(new Player(Sprites.get("Player1_sprites"), new int[] {32, 32}));
        Players.add(new Player(Sprites.get("Player2_sprites"), new int[] {(map_width-2) * 32,  (map_height-2) * 32}));

        //pálya létrehozása
        for (int i=0; i<map_width; i++) {
            for (int j=0; j<map_height; j++) {

                int[] position = new int[] {i*32, j*32};

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
                    Obstacle obstacle = new Obstacle(Sprites.get("BreakableObstacle"), position, true);
                    //TODO: megcsinálni a powerup-ok hozzárendelését
                    Obstacles.add(obstacle);
                }
            }
        }

        //A játék egyetlen ablakának létrehozása
        setDefaultCloseOperation(EXIT_ON_CLOSE);     //így lehet bezárni az ablakot X-szel
        getContentPane().setPreferredSize(new Dimension(map_width * 32 + getInsets().left + getInsets().right, map_height * 32 + getInsets().top + getInsets().bottom));
        setResizable(false);
        pack();
        setVisible(true);

        //ezzel rendeljük hozzá a játékoshoz a billentyűlenyomásokat, hogy player 1-et irányítsuk
        addKeyListener(Players.get(0));
    }

    //játékmechanika megvalósítása
    public void Update () {
        for (Player player: Players) {
            player.Update();
        }
    }

    public static void PlaceBomb(Player bomber){
        //TODO: Bombs.add() annak vizsgálatával, hogy van-e már az adott helyen bomba
        // + hogy szeretnénk megcsinálni a sprite-ok animációit?
    }

    @Override
    public void paint(Graphics g){

        //rajzolás buffereléssel: a villogás elkerülése végett egy MapContent bufferbe rajzolunk,
        //majd azt másoljuk ki az ablakra

        BufferedImage MapContent = new BufferedImage(map_width * 32, map_height * 32, BufferedImage.TYPE_INT_RGB);
        Graphics2D buffer = MapContent.createGraphics();

        // háttér kitöltése színnel
        buffer.setColor(Color.gray);
        buffer.fillRect(0, 0, map_width*32, map_height*32);

        //játékelemek kirajzolása
        for (Obstacle obstacle: Obstacles) {
            buffer.drawImage(obstacle.currentSprite, obstacle.position[0], obstacle.position[1], null);
        }

        for (Player player: Players) {
            buffer.drawImage(player.currentSprite, player.position[0], player.position[1], null);
        }

        //bufferelés
        g.drawImage(MapContent, getInsets().left, getInsets().top,null);
    }
}


