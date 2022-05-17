package Bomberman;

import java.awt.image.BufferedImage;
import static Bomberman.Map.Obstacles;
import static Bomberman.Map.Powerups;

public class Obstacle extends Tile{

    private Powerup Powerup;
    private boolean ContainsPowerup;

    public Obstacle(BufferedImage CurrentSprite, int[] position, boolean isDestroyable) {
        super(CurrentSprite, position);
        this.Destroyable = isDestroyable;
        ContainsPowerup = false;
    }

    public boolean isDestroyable(){
        return this.Destroyable;
    }

    public void AddPowerup(Powerup powerup){
        this.ContainsPowerup = true;
        this.Powerup = powerup;
    }

    public void Destroy() {
        if (Destroyable) {
            if(ContainsPowerup){
                Powerups.add(this.Powerup);
            }
            Obstacles.remove(this);
        }
    }
}
