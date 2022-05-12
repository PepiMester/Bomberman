package Bomberman;


import java.awt.image.BufferedImage;

public class Bomb extends Tile {

    private BufferedImage[] Sprites;
    public double BombTimer;

    private final int spriteHeight = 48;
    private final int spriteWidth = 32;

    public Bomb(BufferedImage sprite_map, int[] position, Player player) {
        super(sprite_map, position);

        this.position[0] = Math.round(this.position[0] / 32) * 32;
        this.position[1] = Math.round(this.position[1] / 32) * 32;

        int cols = sprite_map.getWidth() / spriteWidth;
        Sprites = new BufferedImage[cols];
        for (int col = 0; col < cols; col++) {
            Sprites[col] = sprite_map.getSubimage(col * spriteWidth,  spriteHeight, spriteWidth, spriteHeight);
        }

        currentSprite = Sprites[0];
    }

    @Override
    public void Update() {
        //TODO: animálni, robbantást spawn-olni a map-ra
    }

}
