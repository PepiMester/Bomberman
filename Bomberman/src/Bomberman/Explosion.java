package Bomberman;

import java.awt.image.BufferedImage;

public class Explosion extends Element{

	public double ExplosionTimer;
	public BufferedImage[] Sprites;
	
	public Explosion(BufferedImage[] Sprites, int[] position) {
		super(Sprites[0], position);
	}

	@Override
	public void Update() {
		
	}
	
}
