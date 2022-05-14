package Bomberman;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.util.HashMap;
import java.util.Random;

public class Map{

    public static ArrayList<Explosion> Explosions = new ArrayList<Explosion>();
    public static ArrayList<Player> Players = new ArrayList<Player>();
    public static ArrayList<Bomb> Bombs = new ArrayList<Bomb>();
    public static ArrayList<Powerup> Powerups = new ArrayList<Powerup>();
    public static ArrayList<Obstacle> Obstacles = new ArrayList<Obstacle>();
    public static HashMap<String, BufferedImage> Sprites = new HashMap<String, BufferedImage>();

    //konstansok, hogy itt egyszerre lehessen paraméterezni a mapot, ne kelljen mindenhol átírni
    // ha változtatni akarunk esetleg rajta
    public final int width = 15;
    public final int height = 15;

    public BufferedImage MapContent = new BufferedImage(width * 32, height * 32, BufferedImage.TYPE_INT_RGB);

    private void readSprites() {

        try {
            Sprites.put("UnbreakableObstacle", ImageIO.read(new File("./src/Sprites/wall.png")));
            Sprites.put("BreakableObstacle", ImageIO.read(new File("./src/Sprites/obstacle.png")));
            Sprites.put("Player1_sprites", ImageIO.read(new File("./src/Sprites/player1.png")));
            Sprites.put("Player2_sprites", ImageIO.read(new File("./src/Sprites/player2.png")));
            Sprites.put("Bomb_sprites", ImageIO.read(new File("./src/Sprites/bomb.png")));
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

        //Sprite-ok beolvasása
        readSprites();

        //játékosok lerakása a pálya átelennes sarkaiba
        Players.add(new Player(Sprites.get("Player1_sprites"), new int[] {34, 32}));
        Players.add(new Player(Sprites.get("Player2_sprites"), new int[] {(width -2) * 32 + 2,  (height -2) * 32}));

        //véletlenszám-generátor a power-upokhoz
        Random rand = new Random();

        //pálya létrehozása
        for (int i = 0; i< width; i++) {
            for (int j = 0; j< height; j++) {

                int[] position = new int[] {i*32, j*32};

                //pálya körbe rakása felrobbanthatatlan akadályokkal
                if(i==0 || i== width -1 || j==0 || j== height -1) {
                    Obstacles.add(new Obstacle(Sprites.get("UnbreakableObstacle"), position, false));
                }
                //felrobbanthatatlan akadályok sakktábla szerúen előre megadott potícióban
                else if((i%2==0) && (j%2==0))
                {
                    Obstacles.add(new Obstacle(Sprites.get("UnbreakableObstacle"), position, false));
                }

                //felrobbantható akadályok véletlenszerű eséllyel, de a játékoshoz 2 mezőnél nem közelebb
                else if(!((i==1 && j<4) || (i<4 && j==1) || (i== width -2 && j> height -5) || (i> width -5 && j== height -2)))
                {
                    Obstacle obstacle = new Obstacle(Sprites.get("BreakableObstacle"), position, true);

                    //30%-os eséllyel legyen alatta valamilyen powerup
                    if(rand.nextInt(100) < 30)
                    {
                        PowerupType tipus = null;

                        switch(rand.nextInt(5)){
                            case 0:
                                tipus = PowerupType.POWER_FIREPOWER;
                                break;
                            case 1:
                                tipus = PowerupType.POWER_HEALTH;
                                break;
                            case 2:
                                tipus = PowerupType.POWER_RANGE;
                                break;
                            case 3:
                                tipus = PowerupType.POWER_PIERCE;
                                break;
                            case 4:
                                tipus = PowerupType.POWER_SPEED;
                                break;
                        }

                        Powerup Powerup = new Powerup(tipus, position);
                        Powerups.add(Powerup);
                        obstacle.ContainsPowerup = true;
                        obstacle.Powerup = Powerup;
                    }
                   // Obstacles.add(obstacle);
                }

            }
        }

        for(int i=0;i< Obstacles.size();i++) {
            System.out.println("x " + Obstacles.get(i).position[0] + "y " + Obstacles.get(i).position[1]);
        }
    }

    //játékmechanika megvalósítása
    public void Update () {
        for (Player player: Players) {
            player.Update();
            //TODO: collision detection a player körül (más nem interaktál)
            //TODO: robbanás kezelése: akadályok lebontása, ami alatt van powerup, az jelenjen meg
        }

        for (int i=0; i<Bombs.size(); i++) {
            Bombs.get(i).Update();
        }

        //rajzolás buffereléssel: a villogás elkerülése végett egy MapContent bufferbe rajzolunk,
        //majd azt másoljuk ki az ablakra

        Graphics2D buffer = MapContent.createGraphics();
        buffer.clearRect(0, 0, width * 32, height * 32);

        // háttér kitöltése színnel
        buffer.setColor(Color.gray);
        buffer.fillRect(0, 0, width * 32, height * 32);

        //játékelemek kirajzolása
        for (Obstacle obstacle: Obstacles) {
            if(obstacle.ContainsPowerup){
                buffer.drawImage(obstacle.Powerup.currentSprite, obstacle.position[0], obstacle.position[1], null);
            }else {
                buffer.drawImage(obstacle.currentSprite, obstacle.position[0], obstacle.position[1], null);
            }
        }

        for (Bomb bomb: Bombs) {
            buffer.drawImage(bomb.currentSprite, bomb.position[0], bomb.position[1], null);
        }
        for (Powerup powerup: Powerups) {
            buffer.drawImage(powerup.currentSprite, powerup.position[0], powerup.position[1], null);
        }

        for (Player player: Players) {
            buffer.drawImage(player.currentSprite, player.position[0], player.position[1], null);
        }
    }
}


