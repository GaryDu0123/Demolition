package demolition.role;

import processing.core.PImage;

public class Role {
    private final PImage image;
    private int X;
    private int Y;
    private final String character;

    public Role(int X, int Y, PImage image, String character) {
        this.X = X;
        this.Y = Y;
        this.image = image;
        this.character = character;
    }


    public void setX(int x) {
        X = x;
    }

    public void setY(int y) {
        Y = y;
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }

    public String getCharacter() {
        return character;
    }

    public PImage getImage() {
        return image;
    }

    public int getDisplayX() {
        return X * 32;
    }

    public int getDisplayY() {
        return Y * 32 - 16 + 64;
    }
}
