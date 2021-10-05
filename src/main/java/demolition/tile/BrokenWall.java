package demolition.tile;

import processing.core.PImage;

public class BrokenWall extends Tile {


    private final String character = "B";

    public BrokenWall(int X, int Y, PImage image) {
        super(X, Y, image);
    }


    public String getCharacter() {
        return character;
    }

    @Override
    public String toString() {
        return getCharacter();
    }
}
