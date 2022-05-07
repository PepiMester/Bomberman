package Bomberman;

import java.awt.image.BufferedImage;

public class Obstacle extends Tile{

    public boolean ContainsPowerup;
    public Powerup[] Powerup;

    public Obstacle(BufferedImage CurrentSprite, int[] position, boolean isDestroyable) {
        super(CurrentSprite, position);
    }

    public void Destroy() {

    }

}
