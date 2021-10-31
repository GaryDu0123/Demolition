package demolition.role;


import demolition.core.Resource;
import demolition.enums.Direction;
import demolition.tile.Tile;
import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


/**
 * Enemy, control enemy movement and displacement judgment
 */
public class Enemy extends Role implements Movable {
    private static final ArrayList<Direction> clockwiseDirection = new ArrayList<>(Arrays.asList(Direction.DIRECTION_UP,
            Direction.DIRECTION_RIGHT, Direction.DIRECTION_DOWN, Direction.DIRECTION_LEFT));
    private int counter = 0;
    private int lives = 1;

    /**
     * Create a enemy object.
     *
     * @param X         Generate the X position of the enemy.
     * @param Y         Generate the Y position of the enemy.
     * @param downImg   List of enemy facing down.
     * @param upImg     List of enemy's facing up.
     * @param leftImg   List of enemy's facing left.
     * @param rightImg  List of enemy's facing right.
     * @param character The identification symbol of the enemy object.
     * @param app       PApplet Object
     */
    public Enemy(int X, int Y, PImage[] downImg, PImage[] upImg,
                 PImage[] leftImg, PImage[] rightImg, String character, PApplet app) {
        super(X, Y, downImg, upImg, leftImg, rightImg, character, app);
    }

    @Override
    protected void enemyCollision() {
        if (Resource.player.getX() == this.getX() && Resource.player.getY() == this.getY()) {
            Resource.player.setLivesLoss();
        }
    }

    /**
     * Get Enemy current lives
     *
     * @return Return the player's current lives
     */
    @Override
    public int getLives() {
        return lives;
    }

    /**
     * Subtract 1 from current enemy project
     */
    @Override
    public void setLivesLoss() {
        lives--;
    }

    /**
     * Call this method to move the enemy
     *
     * @param direction Next move direction. This should be AUTO, because the enemy's movement is not controlled
     * @param database  Map database
     */
    @Override
    public void move(Direction direction, Tile[][] database) {
        // can consider getting the frame rate of the app instead - NO, unstable
        if (counter != 60) {
            counter++;
            return;
        }
        counter = 0; // The frame rate counter is reset, once a second,
        // this branch is executed
        Direction nextMoveDirection = null;
        if ("R".equals(getCharacter())) {
            if (!moveValidityCheck(this, getPreDirection(), database)) {
                ArrayList<Direction> validDirection = new ArrayList<>();
                for (Direction d : clockwiseDirection) { // Check the movable direction
                    if (moveValidityCheck(this, d, database)) {
                        validDirection.add(d); // Add to the list of movable directions
                    }
                }

                if (validDirection.size() == 0)
                    // This is no moving direction, walls are all around, don’t move and return
                    return;
                Random random = new Random(); // Choose a method through a random module
                nextMoveDirection = validDirection.get(random.nextInt(validDirection.size()));
            } else {
                nextMoveDirection = getPreDirection();
            }
        } else if ("Y".equals(getCharacter())) {
            int index = clockwiseDirection.indexOf(getPreDirection()); // Pick direction clockwise
            int count = 0;
            while (count < 4) {
                if (index == 4) index = 0;
                if (moveValidityCheck(this, clockwiseDirection.get(index), database)) {
                    nextMoveDirection = clockwiseDirection.get(index);  // if it can move, assign to next
                    break;
                }
                index++;
                count++;
            }
        }

        if (nextMoveDirection == null) { // Walls all around for yellow enemy
            return;
        }
        if (nextMoveDirection != getPreDirection()) { // Reset the existing animation counter to zero
            setActionStatus(0);
            setCounter(0);
            setPreDirection(nextMoveDirection);
        }
        switch (nextMoveDirection) { // Move enemies around
            case DIRECTION_UP:
                setY(getY() - 1);
                enemyCollision();
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
