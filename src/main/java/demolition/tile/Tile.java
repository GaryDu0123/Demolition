package demolition.tile;

import processing.core.PImage;

public class Tile {
    PImage image;
    private int X;
    private int Y;

    public Tile(int X, int Y, PImage image) {
        this.image = image;
        this.X = X;
        this.Y = Y;
    }

    public PImage getImage() {
        return image;
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }
}
