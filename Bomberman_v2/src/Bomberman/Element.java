package Bomberman;

import java.awt.image.BufferedImage;

public abstract class Element {

    protected BufferedImage currentSprite;
    protected int[] position;

    public Element(BufferedImage sprite, int[] position) {
        this.position = new int[2];
        this.position[0] = position[0];
        this.position[1] = position[1];
        this.currentSprite = sprite;
    }

    // ezek úgyis felül lesznek definiálva
    public void Update() {
        return;
    }

    public boolean CollisionDetection(Element element) {
        return false;
    }
}