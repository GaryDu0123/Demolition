package demolition.role;

import demolition.enums.Direction;
import demolition.tile.EmptyTile;
import demolition.tile.GoalTile;
import demolition.tile.Tile;


public interface Movable {
    boolean move(Direction direction, Tile[][] database);

    default boolean moveValidityCheck(Role role, Direction direction, Tile[][] database) {
        switch (direction) {
            case DIRECTION_UP:
                if (role.getY() - 1 >= 0 && (database[role.getY() - 1][role.getX()] instanceof EmptyTile ||
                        database[role.getY() - 1][role.getX()] instanceof GoalTile)) {
                    return true;
                }
                break;
            case DIRECTION_DOWN:
                if (role.getY() + 1 < database.length && (database[role.getY() + 1][role.getX()] instanceof EmptyTile ||
                        database[role.getY() + 1][role.getX()] instanceof GoalTile)) {
                    return true;
                }
                break;
            case DIRECTION_LEFT:
                if (role.getX() - 1 >= 0 && (database[role.getY()][role.getX() - 1] instanceof EmptyTile ||
                        database[role.getY()][role.getX() - 1] instanceof GoalTile)) {
                    return true;
                }
                break;
            case DIRECTION_RIGHT:
                if (role.getX() + 1 < database[0].length && (database[role.getY()][role.getX() + 1] instanceof EmptyTile ||
                        database[role.getY()][role.getX() + 1] instanceof GoalTile)) {
                    return true;
                }
                break;
        }
        return false;
    }

}

