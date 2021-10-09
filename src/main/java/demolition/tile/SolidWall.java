package demolition.tile;

import processing.core.PImage;

public class SolidWall extends Tile {

    public static final String character = "W";

    public SolidWall(int X, int Y, PImage image) {
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

