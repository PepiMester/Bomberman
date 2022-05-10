package Bomberman;


import java.awt.image.BufferedImage;

public class Bomb extends Tile {

    public BufferedImage[] Sprites;
    public double BombTimer;

    public Bomb(BufferedImage[] Sprite, int[] position, Player player) {
        super(Sprite[0], position);
    }

    public void Align() {
        this.position[0] = Math.round(this.position[0] / 32) * 32;
        this.position[1] = Math.round(this.position[1] / 32) * 32;
    }

    @Override
    public void Update() {

    }

}
