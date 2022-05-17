package Bomberman;

import java.awt.image.BufferedImage;

public class Tile extends Element {

    protected boolean Destroyable;
    protected boolean ExplosionOnTile;

    public Tile(BufferedImage sprite, int[] position) {
        super(sprite, position);
        ExplosionOnTile = false;
    }
}
