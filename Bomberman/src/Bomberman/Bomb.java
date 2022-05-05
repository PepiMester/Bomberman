package Bomberman;

import java.awt.image.BufferedImage;

public class Bomb extends Tile {

	public BufferedImage[] Sprites;
	public double BombTimer;
	
	public Bomb(BufferedImage[] Sprite, int[] position, Player player) {
		super(Sprite[0], position);
	}

	public void Align() {
		
	}
	
	@Override
	public void Update() {
		
	}
	
}
