package demolition.tile;

import demolition.core.GameObject;
import demolition.core.Resource;
import processing.core.PImage;

/**
 * Abstract class, which represents the same properties and methods of all static tail classes.
 */
public abstract class Tile extends GameObject {
    private final PImage image;

    /**
     * Create a static resource
     *
     * @param X     X position on the map.
     * @param Y     Y position on the map.
     * @param image PImage of current object
     */
    public Tile(int X, int Y, PImage image) {
        super(X, Y);
        this.image = image;
    }

    /**
     * Return the PImage object, which contains the static image that has been loaded
     *
     * @return PImage object of current Tile which extend Tail.
     */
    public PImage getImage() {
        return image;
    }

    /**
     * Returns the symbol of the current object.
     *
     * @return String of object symbol.
     */
    public abstract String getCharacter();

    /**
     * The call causes the object to be emptied from the map.
     */
    public void die() {
        Resource.mapDatabase[getY()][getX()] = new EmptyTile(getX(), getY(), Resource.empty);
    }

}
