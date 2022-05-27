package Bomberman;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.awt.*;
import javax.imageio.ImageIO;
import java.util.HashMap;
import java.util.Random;

public class Map implements Serializable {

    public static ArrayList<Explosion> Explosions = new ArrayList<Explosion>();
    public static ArrayList<Player> Players = new ArrayList<Player>();
    public static ArrayList<Bomb> Bombs = new ArrayList<Bomb>();
    public static ArrayList<Powerup> Powerups = new ArrayList<Powerup>();
    public static ArrayList<Obstacle> Obstacles = new ArrayList<Obstacle>();
    public transient static HashMap<String, BufferedImage> Sprites = new HashMap<String, BufferedImage>();

    //konstansok, hogy itt egyszerre lehessen paraméterezni a mapot, ne kelljen mindenhol átírni
    // ha változtatni akarunk esetleg rajta
    private static final int width = 15;
    private static final int height = 15;

    private Player loser = null;

    public static int getWidth() {
        return width;
    }

    public static int getHeight() {
        return height;
    }

    public Player getLoser() {
        return loser;
    }

    public BufferedImage MapContent = new BufferedImage(width * 32, height * 32, BufferedImage.TYPE_INT_RGB);

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(MapContent, "png", baos);
        out.writeObject(baos.toByteArray());
        System.out.println("kép kint");
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        MapContent = ImageIO.read(new ByteArrayInputStream((byte[])in.readObject()));
        System.out.println("kép bent");
    }

    public Map()
    {
        //Sprite-ok beolvasása
        readSprites();

        //játékosok lerakása a pálya átelennes sarkaiba
        Players.add(new Player(Sprites.get("Player1_sprites"), new int[] {34, 32}));
        Players.add(new Player(Sprites.get("Player2_sprites"), new int[] {(width -2) * 32 + 2,  (height -2) * 32}));

        generateMap();
    }

    private void readSprites()
    {
        try {
            Sprites.put("UnbreakableObstacle", ImageIO.read(getClass().getClassLoader().getResource("wall.png")));
            Sprites.put("BreakableObstacle", ImageIO.read(getClass().getClassLoader().getResource("obstacle.png")));
            Sprites.put("Player1_sprites", ImageIO.read(getClass().getClassLoader().getResource("player1.png")));
            Sprites.put("Player2_sprites", ImageIO.read(getClass().getClassLoader().getResource("player2.png")));
            Sprites.put("Bomb_sprites", ImageIO.read(getClass().getClassLoader().getResource("bomb.png")));
            Sprites.put("Explosion_sprites", ImageIO.read(getClass().getClassLoader().getResource("explosion.png")));
            Sprites.put("Powerup_HealthBoost", ImageIO.read(getClass().getClassLoader().getResource("healthboost.png")));
            Sprites.put("Powerup_ExtraAmmo", ImageIO.read(getClass().getClassLoader().getResource("extra_bomb.png")));
            Sprites.put("Powerup_Pierce", ImageIO.read(getClass().getClassLoader().getResource("pierce.png")));
            Sprites.put("Powerup_Speed", ImageIO.read(getClass().getClassLoader().getResource("speed.png")));
            Sprites.put("Powerup_Range", ImageIO.read(getClass().getClassLoader().getResource("range.png")));
        }
        catch (IOException e) {
            System.err.println("Nem sikerült beolvasni a képet");
        }
    }

    private void generateMap()
    {
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
                        obstacle.AddPowerup(Powerup);
                    }
                    Obstacles.add(obstacle);
                }
            }
        }
    }

    //játékmechanika megvalósítása
    public void Update () {
        for (Player player : Players) {
            player.Update();
            if (player.getHealth() == 0) {
                this.loser = player;
            }
        }
        for (int i = 0; i < Bombs.size(); i++) {
            Bombs.get(i).Update();
            if (Bombs.get(i).isDetonated()) {
                Explosion.Spawn(Bombs.get(i));
                Bombs.remove(i);
            }
        }
        for (int i = 0; i < Explosions.size(); i++) {
            Explosions.get(i).Update();
            if (Explosions.get(i).isDecayed()) {
                Explosions.remove(i);
            }
        }
        for (int i = 0; i < Obstacles.size(); i++) {
            if (Obstacles.get(i).ExplosionOnTile) {
                Obstacles.get(i).Destroy();
            }
        }
        for (int i = 0; i < Powerups.size(); i++) {
            Powerups.get(i).Update();
            if (Powerups.get(i).ExplosionOnTile && Powerups.get(i).Destroyable) {
                Powerups.remove(i);
            }
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
            buffer.drawImage(obstacle.currentSprite, obstacle.position[0], obstacle.position[1], null);
        }
        for (Bomb bomb: Bombs) {
            buffer.drawImage(bomb.currentSprite, bomb.position[0], bomb.position[1], null);
        }
        for (Powerup powerup: Powerups) {
            buffer.drawImage(powerup.currentSprite, powerup.position[0], powerup.position[1], null);
            if(!powerup.Destroyable){
                buffer.setColor(Color.cyan);
                buffer.drawRect(powerup.position[0], powerup.position[1], 31, 31);
                buffer.drawRect(powerup.position[0] + 1, powerup.position[1] + 1, 29, 29);
            }
        }
        for (Player player: Players) {
            DrawHealthBar(buffer, player);
            buffer.drawImage(player.currentSprite, player.position[0], player.position[1], null);
        }
        for (Explosion explosion: Explosions) {
            buffer.drawImage(explosion.currentSprite, explosion.position[0], explosion.position[1], null);
        }
    }

    private void DrawHealthBar(Graphics2D buffer, Player player){
        buffer.setColor(Color.black);
        buffer.fillRect(player.position[0] - 2, player.position[1] - 8, 31, 5);
        switch (player.getHealth()) {
            case 1:
                buffer.setColor(Color.red);
                break;
            case 2:
                buffer.setColor(Color.orange);
                break;
            case 3:
                buffer.setColor(Color.yellow);
                break;
            case 4:
                buffer.setColor(Color.green);
                break;
            default:
                buffer.setColor(Color.black);
                break;
        }
        buffer.fillRect(player.position[0] - 1, player.position[1] - 7, (31*player.getHealth())/4, 4);
        buffer.setColor(Color.white);
        buffer.drawRect(player.position[0] - 2, player.position[1] - 8, 31, 5);
    }

    public void drawGameOverScreen(boolean win){
        Graphics g = MapContent.createGraphics();
        String msg;
        if(win) {
            g.setColor(Color.GREEN);
            g.fillRect(0, Map.getHeight() * 32 * 2/5, Map.getWidth() * 32, Map.getHeight() * 32 / 5);
            g.setColor(Color.BLACK);
            g.setFont(g.getFont().deriveFont(60f));
            msg = "Győzelem!";
        }else{
            g.setColor(Color.red);
            g.fillRect(0, Map.getHeight() * 32 * 2/5, Map.getWidth() * 32, Map.getHeight() * 32 / 5);
            g.setColor(Color.BLACK);
            g.setFont(g.getFont().deriveFont(60f));
            msg = "Vesztettél!";
        }
        g.drawString(msg, 32 * getWidth() / 2 - (int) g.getFontMetrics().getStringBounds(msg, g).getCenterX(),
                32 * getHeight() / 2 - (int) g.getFontMetrics().getStringBounds(msg, g).getCenterY());
    }
}


