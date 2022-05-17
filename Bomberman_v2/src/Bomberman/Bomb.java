package Bomberman;

import java.awt.image.BufferedImage;
import java.util.Arrays;

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
            Explode();
            this.planter.placed_bombs--;
            Map.Bombs.remove(this);
        }
    }

    private void Explode(){
        //SPAWN EXPLOSION

        final int[] explosion_start_position = this.position.clone();
        int[] current_position = this.position.clone();

        ExplosionType type;

        //robbanás közepe ott, ahol a bomba volt
        Map.Explosions.add(new Explosion(Map.Sprites.get("Explosion_sprites"), current_position, ExplosionType.CENTER));

        //irányonként iterálunk végig az útba eső akadályokon a robbanás generálása során
        ExplosionType[] explosion_head = {ExplosionType.LEFT_END, ExplosionType.RIGHT_END, ExplosionType.TOP_END, ExplosionType.BOTTOM_END};
        ExplosionType[] explosion_column = {ExplosionType.HORIZONTAL, ExplosionType.HORIZONTAL, ExplosionType.VERTICAL, ExplosionType.VERTICAL};

        for(int dir = 0; dir < 4; dir++)
        {
            boolean explosion_end = false;
            int current_pierce = this.planter.getPierce();
            current_position = this.position.clone();

            for(int i=1; i<=this.planter.getRange(); i++){
                current_position[dir / 2] = explosion_start_position[dir / 2] + i * 32 * (dir % 2 > 0 ? 1 : -1);
                for(Obstacle obstacle : Map.Obstacles){
                    if(Arrays.equals(obstacle.position, current_position))
                    {
                        if(obstacle.isDestroyable() && current_pierce!=0)
                        {
                            //doboz
                            obstacle.ExplosionOnTile = true;
                            current_pierce--;
                            break;
                        }else{
                            //fal
                            explosion_end = true;
                            break;
                        }
                    }
                }
                if(explosion_end) {
                    break;
                }else{
                    if(i==this.planter.getRange()){
                        type = explosion_head[dir];
                    }else{
                        type = explosion_column[dir];
                    }
                    Map.Explosions.add(new Explosion(Map.Sprites.get("Explosion_sprites"), current_position, type));
                }
            }
        }
    }
}
