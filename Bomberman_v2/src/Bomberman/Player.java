package Bomberman;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.Arrays;

import static Bomberman.Map.Obstacles;

public class Player extends Element implements KeyListener {

    //statok, alapértékekkel
    private int Health = 2;
    private int Range = 1;
    private int Firepower = 1;
    private int Pierce = 0;
    private int Speed = 1;

    public int placed_bombs = 0;

    //lenyomott billenytűk flag-jei
    private boolean UpPressed = false;
    private boolean DownPressed = false;
    private boolean LeftPressed = false;
    private boolean RightPressed = false;
    private boolean ActionPressed = false;

    private final int game_tick_per_animation_frame = 8;
    private int animation_step = 0;
    private int sprite_direction = 2;
    private BufferedImage[][] Sprites;

    private final int spriteHeight = 36;
    private final int spriteWidth = 28;

    public Player(BufferedImage sprite_map, int[] position) {

        super(sprite_map, position);

        int rows = sprite_map.getHeight() / spriteHeight;
        int cols = sprite_map.getWidth() / spriteWidth;
        Sprites = new BufferedImage[rows][cols];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                Sprites[row][col] = sprite_map.getSubimage(col * spriteWidth, row * spriteHeight, spriteWidth, spriteHeight);
            }
        }

        currentSprite = Sprites[2][0];
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //nyilvántartjuk, mit nyomtak meg
        switch (e.getKeyCode()){
            case KeyEvent.VK_UP:
                UpPressed = true;
                break;
            case KeyEvent.VK_DOWN:
                DownPressed = true;
                break;
            case KeyEvent.VK_LEFT:
                LeftPressed = true;
                break;
            case KeyEvent.VK_RIGHT:
                RightPressed = true;
                break;
            case KeyEvent.VK_SPACE:
                ActionPressed = true;
                break;
            default:
                //debug céllal
                System.out.println("Ezt a billentyűt nyomták le: " + e.getKeyCode());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //nyilvántartjuk, mit engedtek el
        switch (e.getKeyCode()){
            case KeyEvent.VK_UP:
                UpPressed = false;
                break;
            case KeyEvent.VK_DOWN:
                DownPressed = false;
                break;
            case KeyEvent.VK_LEFT:
                LeftPressed = false;
                break;
            case KeyEvent.VK_RIGHT:
                RightPressed = false;
                break;
            case KeyEvent.VK_SPACE:
                ActionPressed = false;
                break;
            default:
                //debug céllal
                System.out.println("Ezt a billentyűt nyomták le: " + e.getKeyCode());
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //erre nincs szükség, csak a KeyListener interface implementálásához kell, hogy létezzen ez a metódus
    }

    //játékos cselekvései
    public void Update() {

        boolean isCollision = Collision();

        if(UpPressed){
            if(!isCollision) {
                position[1] = position[1] - Speed;
            }
            sprite_direction = 3;
            animation_step++;
        }
        if(DownPressed){
            if(!isCollision) {
                position[1] = position[1] + Speed;
            }
            sprite_direction = 2;
            animation_step++;
        }
        if(LeftPressed){
            if(!isCollision) {
                position[0] = position[0] - Speed;
            }
            sprite_direction = 1;
            animation_step++;
        }
        if(RightPressed){
            if(!isCollision) {
                position[0] = position[0] + Speed;
            }
            sprite_direction = 0;
            animation_step++;
        }
        if(ActionPressed){
            PlaceBomb();
        }
        if(animation_step>=game_tick_per_animation_frame*4 || (!LeftPressed && !RightPressed && !UpPressed && !DownPressed)){
            animation_step = 0;
        }
        currentSprite = Sprites[sprite_direction][animation_step/game_tick_per_animation_frame];
    }

    private boolean Collision(){
        //falakra

        int x_min = this.position[0]-spriteWidth-2;
        int x_max = this.position[0]+spriteWidth-1;

        int y_max = this.position[1] + spriteHeight-3;
        int y_min = this.position[1] + spriteHeight / 2 - 32;

        for(int i=0; i<Obstacles.size(); i++)
        {
            //BAL
            if(Obstacles.get(i).position[0]<this.position[0] && LeftPressed && (this.position[0]-Obstacles.get(i).position[0])<32 &&
                    (Obstacles.get(i).position[1] > y_min) && (Obstacles.get(i).position[1] < y_max)) {
                //System.out.println("LEFT");
                return true;
            }
            //JOBB
            if(Obstacles.get(i).position[0]>this.position[0] && RightPressed && (Obstacles.get(i).position[0]-this.position[0])<28 &&
                    (Obstacles.get(i).position[1] > y_min) && (Obstacles.get(i).position[1] < y_max)) {
                //System.out.println("RIGHT");
                return true;
            }
            //FEL
            if(Obstacles.get(i).position[1]<this.position[1] && UpPressed && (this.position[1]-Obstacles.get(i).position[1])<16 &&
                    (Obstacles.get(i).position[0] > x_min) && (Obstacles.get(i).position[0] < x_max)) {
                //System.out.println("UP");
                return true;
            }
            //LE
            if(Obstacles.get(i).position[1]>this.position[1] && DownPressed && (Obstacles.get(i).position[1]-this.position[1])<36 &&
                    (Obstacles.get(i).position[0] > x_min) && (Obstacles.get(i).position[0] < x_max)) {
                //System.out.println("DOWN");
                return true;
            }

            //TODO: a többi irányba is ezzel analóg módon
            /*
            if(Obstacles.get(i).position[0]>this.position[0] && RightPressed && (Obstacles.get(i).position[0]-this.position[0])<5) {
                System.out.println("RIGHT");
                return true; }
            if(Obstacles.get(i).position[1]<this.position[1] && DownPressed && (this.position[1]-Obstacles.get(i).position[1])<5) {
                System.out.println("DOWN");
                return true; }
            if(Obstacles.get(i).position[1]>this.position[1] && UpPressed && (Obstacles.get(i).position[1]-this.position[1])<5) {
                System.out.println("UP");
                return true; }
             */
        }

        //powerupra

        //bombára

        //explosion-re

        //TODO megnézni az összes dolgot, amivel ütközhet
        //kitalálni, hogy a bombával hogyan működjön lerakás után
        return false;
    }

    //bomba lerakás (Player hívja Action gomb hatására)
    private void PlaceBomb(){
        if(placed_bombs < Firepower) {
            Bomb bomb = new Bomb(Map.Sprites.get("Bomb_sprites"), this.position, this);
            for (Bomb existing_bomb : Map.Bombs) {
                if (Arrays.equals(bomb.position, existing_bomb.position)) {
                    return;
                }
            }
            Map.Bombs.add(bomb);
            placed_bombs++;
        }
    }
}
