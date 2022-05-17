package Bomberman;

import java.awt.image.BufferedImage;

import static Bomberman.Map.Obstacles;
import static Bomberman.Map.Powerups;

public class Obstacle extends Tile{

    public boolean ContainsPowerup;
    public Powerup Powerup;

    public Obstacle(BufferedImage CurrentSprite, int[] position, boolean isDestroyable) {
        super(CurrentSprite, position);
        this.Destroyable = isDestroyable;
        ContainsPowerup = false;
    }

    public boolean isDestroyable(){
        return this.Destroyable;
    }

    public void Destroy() {
        if (this.Destroyable) {
            if(this.ContainsPowerup){
                Powerups.add(this.Powerup);
            }
            Obstacles.remove(this);
        }
    }
}
