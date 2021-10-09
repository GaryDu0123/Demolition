package demolition.tile;

import processing.core.PImage;

public class BrokenWall extends Tile {

    public final boolean isBlock = true;
    public static final String character = "B";

    public BrokenWall(int X, int Y, PImage image) {
        super(X, Y, image);
    }

    @Override
    public String getCharacter() {
        return character;
    }

    @Override
    public String toString() {
        return getCharacter();
    }
}
