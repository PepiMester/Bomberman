package Bomberman;

import java.awt.image.BufferedImage;

public class Powerup extends Tile{

    public PowerupType PowerupType;
    public double PowerupTimer;

    public Powerup(BufferedImage sprite, int[] position) {
        super(sprite, position);
    }

    @Override
    public void Update() {

    }

    public void PickupPowerup(Player player) {

    }

}
