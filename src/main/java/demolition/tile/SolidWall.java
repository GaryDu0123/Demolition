package demolition.tile;

import processing.core.PImage;

/**
 * SolidWall
 */
public class SolidWall extends Tile {

    public static final String character = "W";

    /**
     * Create a static resource
     *
     * @param X     X position on the map.
     * @param Y     Y position on the map.
     * @param image PImage of current object
     */
    public SolidWall(int X, int Y, PImage image) {
        super(X, Y, image);
    }

    /**
     * Returns the symbol of the current object.
     *
     * @return String of object symbol.
     */
    @Override
    public String getCharacter() {
        return character;
    }

    /**
     * Returns the identifier of the current object
     *
     * @return Identifier of the current object.
     */
    @Override
    public String toString() {
        return getCharacter();
    }

}

