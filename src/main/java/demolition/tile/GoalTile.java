package demolition.tile;

import processing.core.PImage;

public class GoalTile extends Tile{
    public final boolean isBlock = false;
    private final String character = "G";

    public GoalTile(int X, int Y, PImage image) {
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
