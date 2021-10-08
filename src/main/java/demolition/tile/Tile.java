package demolition.tile;

import demolition.core.GameObject;
import processing.core.PImage;

public class Tile extends GameObject {
    PImage image;

    public Tile(int X, int Y, PImage image) {
        super(X, Y);
        this.image = image;
    }

    public PImage getImage() {
        return image;
    }
}
