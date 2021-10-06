package demolition.role;

import demolition.enums.Direction;
import demolition.interfaces.Movable;
import demolition.tile.*;
import processing.core.PApplet;
import processing.core.PImage;

public class Player extends Role implements Movable {

    public Player(int X, int Y, PImage[] downImg, PImage[] upImg,
                  PImage[] leftImg, PImage[] rightImg, String character, PApplet app) {
        super(X, Y, downImg, upImg, leftImg, rightImg, character, app);
    }

    @Override
    public boolean move(Direction direction, Tile[][] database) {
        if (direction != getPreDirection()){
            setActionStatus(0);
            setCounter(0);
            setPreDirection(direction);;
        }
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
