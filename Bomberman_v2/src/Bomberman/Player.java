package Bomberman;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;

import static Bomberman.Map.*;

public class Player extends Element implements KeyListener {

    //statok, alapértékekkel
    private int Health = 2;
    private int Range = 1;
    private int Firepower = 1;
    private int Pierce = 1;
    private int Speed = 1;

    private int placed_bombs = 0;
    private ArrayList<Explosion> damagedByExplosionOrigin = new ArrayList<>();

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

    public char getAction(){
        char action = 0;
        if(this.UpPressed){
            action |= (0x01);
        }
        if(this.DownPressed){
            action |= (0x02);
        }
        if(this.LeftPressed){
            action |= (0x04);
        }
        if(this.RightPressed){
            action |= (0x08);
        }
        if(this.ActionPressed){
            action |= (0x10);
        }
        return action;
    }

    public void setAction(char action){
        this.UpPressed = ((action & (0x01)) != 0);
        this.DownPressed = ((action & (0x02)) != 0);
        this.LeftPressed = ((action & (0x04)) != 0);
        this.RightPressed = ((action & (0x08)) != 0);
        this.ActionPressed = ((action & (0x10)) != 0);
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

    private void PlaceBomb() {
        if(placed_bombs < Firepower) {
            Bomb bomb = new Bomb(Map.Sprites.get("Bomb_sprites"), this.position, this);
            for (Bomb existing_bomb : Bombs) {
                if (Arrays.equals(bomb.position, existing_bomb.position)) {
                    return;
                }
            }
            Bombs.add(bomb);
            placed_bombs++;
        }
    }

    public void restock(){
        if(placed_bombs>0) {
            placed_bombs--;
        }
    }

    public void increaseFirepower(){
        if(this.Firepower < 3) {
            this.Firepower++;
        }
    }

    public void increaseRange(){
        this.Range++;
    }

    public void increaseHealth(){
        if(this.Health < 4){
            this.Health++;
        }
    }

    public void decreaseHealth(){
        this.Health--;
    }

    public void increasePierce(){
        this.Pierce++;
    }

    public void increaseSpeed(){
        if(this.Speed < 3){
            this.Speed++;
        }
    }

    public int getPierce() {
        return Pierce;
    }

    public int getRange() {
        return Range;
    }

    public int getHealth() { return Health; }

    private boolean Collision(){
        //falakra

        int x_min = this.position[0] - spriteWidth - 2 + 3;
        int x_max = this.position[0] + spriteWidth - 1 - 3;

        int y_min = this.position[1] + spriteHeight / 2 - 32 + 3;
        int y_max = this.position[1] + spriteHeight - 3 - 3;

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
            if(Obstacles.get(i).position[1]>this.position[1] && DownPressed && (Obstacles.get(i).position[1]-this.position[1])<37 &&
                    (Obstacles.get(i).position[0] > x_min) && (Obstacles.get(i).position[0] < x_max)) {
                //System.out.println("DOWN");
                return true;
            }
        }

        //powerupra
        for(int i=0 ; i < Powerups.size(); i++){
            //BAL
             if(Powerups.get(i).position[0]<this.position[0] && LeftPressed && (this.position[0]-Powerups.get(i).position[0])<32&&
                     (Powerups.get(i).position[1] > y_min) && (Powerups.get(i).position[1] < y_max)) {
                 Powerups.get(i).PickupPowerup(this);
                 Powerups.remove(i);
                 return true;
             }
             //JOBB
             if(Powerups.get(i).position[0]>this.position[0] && RightPressed && (Powerups.get(i).position[0]-this.position[0])<28&&
                     (Powerups.get(i).position[1] > y_min) && (Powerups.get(i).position[1] < y_max)) {
                 Powerups.get(i).PickupPowerup(this);
                 Powerups.remove(i);
                 return true;
             }
             //FEL
             if(Powerups.get(i).position[1]<this.position[1] && UpPressed && (this.position[1]-Powerups.get(i).position[1])<16&&
                     (Powerups.get(i).position[0] > x_min) && (Powerups.get(i).position[0] < x_max)) {
                 Powerups.get(i).PickupPowerup(this);
                 Powerups.remove(i);
                 return true;
             }
             //LE
             if(Powerups.get(i).position[1]>this.position[1] && DownPressed && (Powerups.get(i).position[1]-this.position[1])<36&&
                     (Powerups.get(i).position[0] > x_min) && (Powerups.get(i).position[0] < x_max)) {
                 Powerups.get(i).PickupPowerup(this);
                 Powerups.remove(i);
                 return true;
            }
        }

        //explosion-re
        for(int i=0 ; i < Explosions.size(); i++){
            if(x_min < Explosions.get(i).position[0] &&  Explosions.get(i).position[0] < x_max &&
                    y_min < Explosions.get(i).position[1] && Explosions.get(i).position[1] < y_max)  {
                if(!damagedByExplosionOrigin.contains(Explosions.get(i).getOrigin())) {
                    damagedByExplosionOrigin.add(Explosions.get(i).getOrigin());
                    decreaseHealth();
                }
            }
        }
        for(int i=0; i < damagedByExplosionOrigin.size(); i++){
            boolean explosion_decayed = true;
            for(int j=0; j<Explosions.size(); j++){
                if(Explosions.get(j).getOrigin().equals(damagedByExplosionOrigin.get(i))){
                    explosion_decayed = false;
                    break;
                }
            }
            if(explosion_decayed){
                damagedByExplosionOrigin.remove(i);
            }
        }

        return false;
    }
}
