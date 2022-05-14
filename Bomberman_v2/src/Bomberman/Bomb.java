package Bomberman;


import java.awt.image.BufferedImage;

public class Bomb extends Tile {

    private BufferedImage[] Sprites;
    private Player planter;
    public int BombTimer;
    public boolean detonated = false;

    private final int game_ticks_till_explosion = 100;
    private final int animation_speed = 5;

    private final int spriteHeight = 32;
    private final int spriteWidth = 32;

    public Bomb(BufferedImage sprite_map, int[] position, Player player) {
        super(sprite_map, position);

        this.planter = player;

        this.position[0] = Math.round((this.position[0] + this.spriteWidth / 2) / 32) * 32;
        this.position[1] = Math.round((this.position[1] + this.spriteHeight * 2 / 3) / 32) * 32;

        int cols = sprite_map.getWidth() / spriteWidth;
        Sprites = new BufferedImage[cols];
        for (int col = 0; col < cols; col++) {
            Sprites[col] = sprite_map.getSubimage(col * spriteWidth,  0, spriteWidth, spriteHeight);
        }

        currentSprite = Sprites[0];
    }

    @Override
    public void Update() {
        BombTimer++;
        currentSprite = Sprites[(BombTimer / animation_speed) % 10];
        if(BombTimer >= game_ticks_till_explosion){
            Map.Bombs.remove(this);
            this.planter.placed_bombs--;
            //TODO: SPAWN EXPLOSION
        }
    }

}
