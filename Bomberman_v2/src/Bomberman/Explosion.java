package Bomberman;

import java.awt.image.BufferedImage;

public class Explosion extends Element{

    public int ExplosionTimer;
    private BufferedImage[][] Sprites;

    private ExplosionType explosiontype;

    private final int spriteHeight = 32;
    private final int spriteWidth = 32;

    private final int animation_speed = 2;

    public Explosion(BufferedImage sprite_map, int[] position, ExplosionType type) {
        super(sprite_map, position);

        int rows = sprite_map.getHeight() / spriteHeight;
        int cols = sprite_map.getWidth() / spriteWidth;
        Sprites = new BufferedImage[rows][cols];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                Sprites[row][col] = sprite_map.getSubimage(col * spriteWidth, row * spriteHeight, spriteWidth, spriteHeight);
            }
        }

        explosiontype = type;

        switch (explosiontype){
            case CENTER:
                currentSprite = Sprites[0][0];
                break;
            case HORIZONTAL:
                currentSprite = Sprites[1][0];
                break;
            case VERTICAL:
                currentSprite = Sprites[2][0];
                break;
            case LEFT_END:
                currentSprite = Sprites[3][0];
                break;
            case RIGHT_END:
                currentSprite = Sprites[4][0];
                break;
            case TOP_END:
                currentSprite = Sprites[5][0];
                break;
            case BOTTOM_END:
                currentSprite = Sprites[6][0];
                break;
        }
        ExplosionTimer = 0;
    }

    @Override
    public void Update() {
        // Animate sprite
        ExplosionTimer++;
        int sprite_index = (ExplosionTimer / animation_speed) % 10;

        if(sprite_index==9){
            //itt lesz vége az animációnak
            Map.Explosions.remove(this);
            //TODO rombolni
        }
        else
        {
            switch (explosiontype) {
                case CENTER:
                    currentSprite = Sprites[0][sprite_index];
                    break;
                case HORIZONTAL:
                    currentSprite = Sprites[1][sprite_index];
                    break;
                case VERTICAL:
                    currentSprite = Sprites[2][sprite_index];
                    break;
                case LEFT_END:
                    currentSprite = Sprites[3][sprite_index];
                    break;
                case RIGHT_END:
                    currentSprite = Sprites[4][sprite_index];
                    break;
                case TOP_END:
                    currentSprite = Sprites[5][sprite_index];
                    break;
                case BOTTOM_END:
                    currentSprite = Sprites[6][sprite_index];
                    break;
            }
        }
    }
}
