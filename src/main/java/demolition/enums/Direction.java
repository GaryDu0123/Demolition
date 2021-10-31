package demolition.enums;

import demolition.tile.Tile;

/**
 * Enum of the moving direction of the Roles
 *
 * @see demolition.role.Movable#move(Direction, Tile[][])
 */
public enum Direction {
    DIRECTION_UP,
    DIRECTION_RIGHT,
    DIRECTION_DOWN,
    DIRECTION_LEFT,
    AUTO;
}
