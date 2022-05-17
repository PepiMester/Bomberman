package Bomberman;

import java.awt.image.BufferedImage;
import java.util.Arrays;

import static Bomberman.Map.Explosions;

public class Explosion extends Element{

    private int ExplosionTimer;
    private BufferedImage[][] Sprites;
    private ExplosionType explosiontype;

    private final int spriteHeight = 32;
    private final int spriteWidth = 32;
    private final int animation_speed = 2;
    private boolean decayed = false;

    public static void Spawn(Bomb bomb){
        //SPAWN EXPLOSION

        final int[] explosion_start_position = bomb.position.clone();
        int[] current_position = bomb.position.clone();

        ExplosionType type;

        //robbanás közepe ott, ahol a bomba volt
        Explosions.add(new Explosion(Map.Sprites.get("Explosion_sprites"), current_position, ExplosionType.CENTER));

        //irányonként iterálunk végig az útba eső akadályokon a robbanás generálása során
        ExplosionType[] explosion_head = {ExplosionType.LEFT_END, ExplosionType.RIGHT_END, ExplosionType.TOP_END, ExplosionType.BOTTOM_END};
        ExplosionType[] explosion_column = {ExplosionType.HORIZONTAL, ExplosionType.HORIZONTAL, ExplosionType.VERTICAL, ExplosionType.VERTICAL};

        for(int dir = 0; dir < 4; dir++)
        {
            boolean explosion_end = false;
            int current_pierce = bomb.getPlanter().getPierce();
            current_position = bomb.position.clone();

            for(int i=1; i<=bomb.getPlanter().getRange(); i++){
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
                    if(i==bomb.getPlanter().getRange()){
                        type = explosion_head[dir];
                    }else{
                        type = explosion_column[dir];
                    }
                    Explosions.add(new Explosion(Map.Sprites.get("Explosion_sprites"), current_position, type));
                }
            }
        }
    }

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
        DrawExplosion(0);
        ExplosionTimer = 0;
    }

    public boolean isDecayed(){
        return decayed;
    }

    @Override
    public void Update() {
        // Animate sprite
        ExplosionTimer++;
        int sprite_index = (ExplosionTimer / animation_speed) % 10;

        if(sprite_index==9){
            //itt lesz vége az animációnak
            decayed = true;
        } else {
            DrawExplosion(sprite_index);
        }
    }

    private void DrawExplosion(int sprite_index){
        switch (this.explosiontype) {
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
