package demolition.tile;

import processing.core.PImage;

public class EmptyTile extends Tile {

    private final String character = " ";

    public EmptyTile(int X, int Y, PImage image) {
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
