package demolition.tile;

import processing.core.PImage;

/**
 * GoalTile
 */
public class GoalTile extends Tile{

    public static final String character = "G";

    /**
     * Create a static resource
     *
     * @param X     X position on the map.
     * @param Y     Y position on the map.
     * @param image PImage of current object
     */
    public GoalTile(int X, int Y, PImage image) {
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
