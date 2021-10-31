package demolition.role;

import demolition.core.DynamicObject;
import demolition.enums.Direction;
import processing.core.PApplet;
import processing.core.PImage;

import java.util.HashSet;

/**
 * Enemies and players.
 */
public abstract class Role extends DynamicObject {
    /**
     * PApplet object.
     */
    public PApplet app;
    // PImage of role object
    private final PImage[] downImg;
    private final PImage[] upImg;
    private final PImage[] leftImg;
    private final PImage[] rightImg;
    private final String character;
    private int counter = 0;
    private int actionStatus = 0;
    private Direction preDirection = Direction.DIRECTION_DOWN;
    private final HashSet<Integer> explosionCounterSet = new HashSet<>();

    /**
     * Create a Role object.
     *
     * @param X         Generate the X position of the role.
     * @param Y         Generate the Y position of the role.
     * @param downImg   List of role facing down.
     * @param upImg     List of roles facing up.
     * @param leftImg   List of roles facing left.
     * @param rightImg  List of roles facing right.
     * @param character The identification symbol of the role object.
     * @param app       PApplet Object
     */
    public Role(int X, int Y, PImage[] downImg, PImage[] upImg,
                PImage[] leftImg, PImage[] rightImg, String character, PApplet app) {
        super(X, Y);
        this.downImg = downImg;
        this.upImg = upImg;
        this.leftImg = leftImg;
        this.rightImg = rightImg;
        this.character = character;
        this.app = app;
    }

    /**
     * Return to the list of exploded objects to prevent multiple explosions and repeated blood loss.
     *
     * @return Explosion Counter Set
     */
    public HashSet<Integer> getExplosionCounterSet() {
        return explosionCounterSet;
    }

    /**
     * Return PApplet Object.
     *
     * @return PApplet Object
     */
    public PApplet getApp() {
        return app;
    }

    /**
     * Returns the symbol of the current object.
     *
     * @return String of object symbol.
     */
    public String getCharacter() {
        return character;
    }

    /**
     * Returns the current facing direction of the character.
     *
     * @return Direction enum, present every direction.
     * @see Direction
     */
    public Direction getPreDirection() {
        return preDirection;
    }

    /**
     * Set the player's image counter.
     *
     * @param counter Number between 1 - 59
     */
    public void setCounter(int counter) {
        this.counter = counter;
    }

    /**
     * Set the status of the player's current display image.
     *
     * @param actionStatus Number of status depend on image size.
     */
    public void setActionStatus(int actionStatus) {
        this.actionStatus = actionStatus;
    }

    /**
     * Set the player's current heading.
     *
     * @param preDirection Direction Enum
     * @see Direction
     */
    public void setPreDirection(Direction preDirection) {
        this.preDirection = preDirection;
    }

    /**
     * Returns the coordinates X displayed on the map.
     *
     * @return Display coordinate X.
     */
    @Override
    public int getDisplayX() {
        return getX() * 32;
    }

    /**
     * Returns the coordinates Y displayed on the map.
     * <ul>
     *     <li>
     *         The length of each block * the number of blocks - the height of the character larger than the block + UI part
     *     </li>
     * </ul>
     *
     * @return Display coordinate Y.
     */
    @Override
    public int getDisplayY() {
        return getY() * 32 - 16 + 64;
    }

    /**
     * Draw the player's current state on the window. If the life is exhausted, the object itself is returned,
     * used for outer judgment and removed from the list.
     *
     * @return The object that needs to be destroyed, if there is no object that needs to be destroyed, return null.
     */
    public Role draw() {
        // If role lives less zero, return object itself for outer to remove.
        if (this.getLives() <= 0)
            return this;
        // Calculate the index of the displayed image.
        if (actionStatus == 4)
            actionStatus = 0;
        // Change the display list when the direction is switched
        PImage[] image;
        switch (preDirection) {
            case DIRECTION_DOWN:
                image = downImg;
                break;
            case DIRECTION_UP:
                image = upImg;
                break;
            case DIRECTION_LEFT:
                image = leftImg;
                break;
            case DIRECTION_RIGHT:
                image = rightImg;
                break;
            default:
                return null;
        }
        // Change the display list when the direction is switched
        getApp().image(image[actionStatus], getDisplayX(), getDisplayY());

        if (counter % 12 == 0) { // 0.2 * app.frameRate
            actionStatus++;
            counter = 0;
        }
        counter++;
        return null;
    }

    /**
     * If the player encounters an enemy, or an enemy encounters a player, the player will be deducted lives
     */
    protected abstract void enemyCollision();

    /**
     * Get current lives
     *
     * @return Return the player's current lives
     */
    public abstract int getLives();

    /**
     * Subtract 1 from current role project
     */
    public abstract void setLivesLoss();
}
