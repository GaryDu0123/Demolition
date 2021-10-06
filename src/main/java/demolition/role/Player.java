package demolition.role;

import demolition.enums.Direction;
import demolition.interfaces.Movable;
import demolition.tile.*;
import processing.core.PImage;

public class Player extends Role implements Movable {

    public Player(int X, int Y, PImage image, String character) {
        super(X, Y, image, character);
    }

    @Override
    public boolean move(Direction direction, Tile[][] database) {
        if (moveValidityCheck(this, direction, database)) {
            switch (direction) {
                case DIRECTION_UP:
                    setY(getY() - 1);
                    return true;
                case DIRECTION_DOWN:
                    setY(getY() + 1);
                    return true;
                case DIRECTION_LEFT:
                    setX(getX() - 1);
                    return true;
                case DIRECTION_RIGHT:
                    setX(getX() + 1);
                    return true;
            }
        }
        return false;
    }
}
