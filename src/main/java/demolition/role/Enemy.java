package demolition.role;

import demolition.enums.Direction;
import demolition.interfaces.Movable;
import demolition.tile.Tile;
import processing.core.PImage;

public class Enemy extends Role implements Movable {

    public Enemy(int X, int Y, PImage image, String character) {
        super(X, Y, image, character);
    }

    @Override
    public boolean move(Direction direction, Tile[][] database) {
        return false;
    }
}
