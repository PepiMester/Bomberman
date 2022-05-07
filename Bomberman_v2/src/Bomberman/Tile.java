package Bomberman;

import java.awt.image.BufferedImage;

public class Tile extends Element {

    public boolean Destroyable;
    public boolean ExplosionOnTile;

    public Tile(BufferedImage sprite, int[] position) {
        super(sprite, position);
    }

}
