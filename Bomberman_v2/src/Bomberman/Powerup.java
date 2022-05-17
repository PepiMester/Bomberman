package Bomberman;

public class Powerup extends Tile{

    private PowerupType PowerupType;
    private int PowerupTimer = 0;

    public Powerup(PowerupType poweruptype, int[] position) {
        super(null, position);
        this.PowerupType = poweruptype;
        this.Destroyable = false;
        this.ExplosionOnTile = false;

        switch (poweruptype){
            case POWER_FIREPOWER:
                currentSprite = Map.Sprites.get("Powerup_ExtraAmmo");
                break;
            case POWER_RANGE:
                currentSprite = Map.Sprites.get("Powerup_Range");
                break;
            case POWER_HEALTH:
                currentSprite = Map.Sprites.get("Powerup_HealthBoost");
                break;
            case POWER_PIERCE:
                currentSprite = Map.Sprites.get("Powerup_Pierce");
                break;
            case POWER_SPEED:
                currentSprite = Map.Sprites.get("Powerup_Speed");
                break;
        }
    }

    @Override
    public void Update() {
        if(!this.Destroyable){
            PowerupTimer++;
            if(PowerupTimer>50){
                this.Destroyable = true;
            }
        }
    }

    public void PickupPowerup(Player player) {
        switch (this.PowerupType){
            case POWER_FIREPOWER:
                player.increaseFirepower();
                break;
            case POWER_RANGE:
                player.increaseRange();
                break;
            case POWER_HEALTH:
                player.increaseHealth();
                System.out.println("New health: " + player.getHealth());
                break;
            case POWER_PIERCE:
                player.increasePierce();
                break;
            case POWER_SPEED:
                player.increaseSpeed();
                break;
        }
    }
}
