package demolition.interfaces;

import demolition.enums.Direction;
import demolition.role.Role;
import demolition.tile.EmptyTile;
import demolition.tile.Tile;


public interface Movable {
    boolean move(Direction direction, Tile[][] database);

    default boolean moveValidityCheck(Role role, Direction direction, Tile[][] database) {
        switch (direction) {
            case DIRECTION_UP:
                if (role.getY() - 1 >= 0 && database[role.getY() - 1][role.getX()] instanceof EmptyTile) {
//                    role.setY(role.getY() - 1);
                    return true;
                }
                break;
            case DIRECTION_DOWN:
                if (role.getY() + 1 < database.length && database[role.getY() + 1][role.getX()] instanceof EmptyTile) {
//                    role.setY(role.getY() + 1);
                    return true;
                }
                break;
            case DIRECTION_LEFT:
                if (role.getX() - 1 >= 0 && database[role.getY()][role.getX() - 1] instanceof EmptyTile) {
//                    role.setX(role.getX() - 1);
                    return true;
                }
                break;
            case DIRECTION_RIGHT:
                if (role.getX() + 1 < database[0].length && database[role.getY()][role.getX() + 1] instanceof EmptyTile) {
//                    role.setX(role.getX() + 1);
                    return true;
                }
                break;
        }
        return false;
    }
}

