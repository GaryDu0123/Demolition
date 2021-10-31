package demolition.role;

import demolition.enums.Direction;
import demolition.tile.EmptyTile;
import demolition.tile.GoalTile;
import demolition.tile.Tile;

/**
 * The interface is implemented by movable characters on the map,
 * and the coordinates can be changed to achieve interaction
 */
public interface Movable {
    /**
     * Call this method to move the object that implements the interface
     *
     * @param direction Next move direction.
     * @param database  Map database
     */
    void move(Direction direction, Tile[][] database);

    /**
     * Check if the incoming direction is movable.
     *
     * @param role      Objects that inherit Role
     * @param direction The direction to move next
     * @param database  Map database
     * @return Can it be moved, if it can return true, otherwise return false
     */
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

