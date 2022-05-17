package Bomberman;

public class Powerup extends Tile{

    public PowerupType PowerupType;

    public Powerup(PowerupType poweruptype, int[] position) {
        super(null, position);
        this.PowerupType = poweruptype;

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
