package Bomberman;

import java.awt.image.BufferedImage;

import static Bomberman.Map.Obstacles;

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
            Obstacles.remove(this);
        }
    }
}
