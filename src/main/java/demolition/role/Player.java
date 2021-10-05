package demolition.role;

import processing.core.PImage;

public class Player {
//    private String color;
    private static final String character = "P";
    private int X;
    private int Y;
    PImage image;

    public Player(int X, int Y, PImage image) {
        this.X = X;
        this.Y = Y;
        this.image = image;
    }

    public String getCharacter() {
        return character;
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }

    public PImage getImage() {
        return image;
    }
}
