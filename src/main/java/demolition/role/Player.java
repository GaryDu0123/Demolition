package demolition.role;


import demolition.core.DynamicObject;
import demolition.core.Resource;
import demolition.enums.Direction;
import demolition.tile.*;
import processing.core.PApplet;
import processing.core.PImage;

/**
 * Player, control player movement and displacement judgment.
 */
public class Player extends Role implements Movable {

    /**
     * Create a player object, there can only be one player object on the map,
     * and the extra one will be covered by the following.
     *
     * @param X         Generate the X position of the player.
     * @param Y         Generate the Y position of the player.
     * @param downImg   List of players facing down.
     * @param upImg     List of players facing up.
     * @param leftImg   List of players facing left.
     * @param rightImg  List of players facing right.
     * @param character The identification symbol of the player object.
     * @param app       PApplet Object
     */
    public Player(int X, int Y, PImage[] downImg, PImage[] upImg,
                  PImage[] leftImg, PImage[] rightImg, String character, PApplet app) {
        super(X, Y, downImg, upImg, leftImg, rightImg, character, app);
    }

    /**
     * If the player encounters an enemy, or an enemy encounters a player, the player will be deducted lives.
     */
    @Override
    protected void enemyCollision() {
        for (DynamicObject enemy : Resource.dynamicObjects) {
            if (enemy instanceof Enemy && Resource.player.getX() == enemy.getX() && Resource.player.getY() == enemy.getY()) {
                Resource.player.setLivesLoss();
            }
        }
    }

    /**
     * Move the player, calling this method will make the player move.
     * If the selected direction can be moved, return true and move, if not, return false
     *
     * @param direction The direction the player is moving, Direction Enum
     * @param database  Map database
     */
    @Override
    public void move(Direction direction, Tile[][] database) {
        // If the direction of movement is different from the last time, reset the status counter
        if (direction != getPreDirection()) {
            setActionStatus(0);
            setCounter(0);
        }
        // Check if the next step is actionable
        if (moveValidityCheck(this, direction, database)) {
            setPreDirection(direction); // Set orientation
            switch (direction) {
                case DIRECTION_UP:
                    setY(getY() - 1); // move
                    enemyCollision(); // Determine whether to collide with the enemy
                    return;
                case DIRECTION_DOWN:
                    setY(getY() + 1);
                    enemyCollision();
                    return;
                case DIRECTION_LEFT:
                    setX(getX() - 1);
                    enemyCollision();
                    return;
                case DIRECTION_RIGHT:
                    setX(getX() + 1);
                    enemyCollision();
            }
        }
    }

    /**
     * Get player current lives
     *
     * @return Return the player's current lives
     */
    @Override
    public int getLives() {
        return Resource.lives;
    }

    /**
     * Subtract 1 from current player project
     */
    @Override
    public void setLivesLoss() {
        Resource.lives--;
        Resource.mapStaticRecourseInitUpdate(Resource.currentLevel, app);
    }
}
