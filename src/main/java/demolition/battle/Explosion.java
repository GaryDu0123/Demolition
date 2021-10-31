package demolition.battle;


import demolition.core.DynamicObject;
import demolition.core.Resource;
import demolition.role.Enemy;
import demolition.role.Player;
import demolition.role.Role;
import demolition.tile.*;
import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Explosion type, instantiated by Bomb, controlling the display of explosion images and causing damage.
 */
public class Explosion extends DynamicObject {
    /**
     * PApplet object.
     */
    public PApplet app;

    /**
     * Counter count
     */
    private int counter = 0;

    /**
     * Map database
     */
    private final Tile[][] database;

    /**
     * A list of all roles on the map for easy judgment.
     */
    private final ArrayList<Role> roleList = new ArrayList<>();

    /**
     * List to store all explosion object in class.
     */
    private final ArrayList<AnimationModule> animation = new ArrayList<>();

    /**
     * List of coordinates to check if there are players or enemies in.
     */
    private final ArrayList<int[]> deathCheck = new ArrayList<>(); // Find the death points of players and enemies

    /**
     * Explosion event times counter.
     */
    private static int explosionCounter = 0;

    /**
     * The serial number of the explosion to prevent repeated damage.
     */
    private final int damagedCount;

    /**
     * The explosion class created by the bomb explosion, the coordinates are provided by the bomb.
     * <p>
     * The explosion class will detect all the blocks that can be destroyed and the players or enemies that can be hurt.
     *
     * @param x        The center coordinate x of the explosion.
     * @param y        The center coordinate y of the explosion.
     * @param database Map database.
     * @param player   Player Object.
     * @param enemies  Enemy list.
     * @param app      PApplet object.
     */
    public Explosion(int x, int y, Tile[][] database, Player player, LinkedList<DynamicObject> enemies, PApplet app) {
        super(x, y);
        this.app = app;
        this.database = database;
        roleList.add(player);
        for (DynamicObject enemy : enemies) {
            if (enemy instanceof Enemy)
                roleList.add((Enemy) enemy);
        }
        rangeDetection();
        damagedCount = explosionCounter;
    }

    /**
     * Innerclass of every single explosion animation.
     */
    static class AnimationModule {
        public int x;
        public int y;
        PImage image;

        /**
         * Treat all the small squares affected by the explosion as an object
         *
         * @param x     Coordinates X of the explosion cube
         * @param y     Coordinates Y of the explosion cube
         * @param image PImage of explosion cube
         */
        public AnimationModule(int x, int y, PImage image) {
            this.x = x;
            this.y = y;
            this.image = image;
        }

        /**
         * Return Display Coordinates X
         *
         * @return Display Coordinates X
         */
        public int getDisplayX() {
            return x * 32;
        }

        /**
         * Return Display Coordinates Y
         *
         * @return Display Coordinates Y
         */
        public int getDisplayY() {
            return y * 32 + 64;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getDisplayX() {
        return getX() * 32;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getDisplayY() {
        return getY() * 32 + 64;
    }

    /**
     * {@inheritDoc}
     */
    public Explosion draw() {
        if (counter == 30) { // The explosion reaches 30 frames (0.5 seconds), explosion over
            return this;
        }

        for (Role role : roleList) { // Traverse the list of characters and check if they are included
            for (int[] location : deathCheck) {  // Traverse the list to be checked
                if (role.getX() == location[0] && role.getY() == location[1] && !role.getExplosionCounterSet().contains(damagedCount)) {
                    role.getExplosionCounterSet().add(damagedCount);
                    role.setLivesLoss(); // subtract lives
                    break;
                }
            }
        }
        // Show all small animations on the windows
        for (AnimationModule animationModule : animation) {
            app.image(animationModule.image, animationModule.getDisplayX(), animationModule.getDisplayY());
        }
        counter++; // counter count time
        return null;
    }

    /**
     * Check all areas to explode centered on x, y, And initialize the deathCheck list.
     * <ul>
     * <li>
     *     First find the explosion range, the variables are repeated until 2 grids or obstacles are encountered,
     *     the entity and the destructible are considered separately, the coordinate points are recorded,
     *     and the database is modified
     * </li>
     * <li>
     *     Second. Traverse people through the coordinate points to see if anyone is injured
     * </li>
     * <li>
     *     Finally. Return the injured person and the enemy to be deleted {Or do people directly reduce their blood? Only return the enemy to be deleted, and check the player's condition at the beginning of the loop
     * </li>
     * </ul>
     *
     * @see Explosion#deathCheck
     */
    public void rangeDetection() {


        ArrayList<Tile> tails = new ArrayList<>(2);

        // Create coordinates that need to be checked
        for (int step = 1; step <= 2; step++) { // 1 2  Move Downward
            if (getY() + step < database.length)
                tails.add(database[getY() + step][getX()]);
        }
        checkProcess(tails, deathCheck, Resource.explosionVerticalImg, Resource.explosionEndBottomImg);

        // Create coordinates that need to be checked
        tails = new ArrayList<>(2);
        for (int step = -1; step > -3; step--) { // -1 -2
            if (getY() + step >= 0)
                tails.add(database[getY() + step][getX()]);
        }
        checkProcess(tails, deathCheck, Resource.explosionVerticalImg, Resource.explosionEndTopImg);

        // Create coordinates that need to be checked
        tails = new ArrayList<>(2);
        for (int step = 1; step <= 2; step++) { // 1 2
            if (getX() + step < database[0].length)
                tails.add(database[getY()][getX() + step]);
        }
        checkProcess(tails, deathCheck, Resource.explosionHorizontalImg, Resource.explosionEndRightImg);

        // Create coordinates that need to be checked
        tails = new ArrayList<>(2);
        for (int step = -1; step > -3; step--) { // 1 2
            if (getX() + step >= 0)
                tails.add(database[getY()][getX() + step]);
        }
        checkProcess(tails, deathCheck, Resource.explosionHorizontalImg, Resource.explosionEndLeftImg);

        // Add the middle position to the position to be checked (the explosion center point)
        deathCheck.add(new int[]{getX(), getY(), damagedCount}); //
        animation.add(new AnimationModule(getX(), getY(), Resource.explosionCenterImg)); // Add a centered picture
        explosionCounter++;
    }

    /**
     * Find all static resources that exploded or destroyed
     *
     * @param tile       Tile coordinate list
     * @param deathCheck List of death checks required
     * @param middle     Refers to the middle of all explosive branches
     * @param edge       Refers generally to the back of all explosive branches
     */
    private void checkProcess(ArrayList<Tile> tile, ArrayList<int[]> deathCheck, PImage middle, PImage edge) {
        if (tile.size() == 0) return;
        if (tile.get(0).getCharacter().equals(EmptyTile.character) || tile.get(0).getCharacter().equals(GoalTile.character)) {
            switch (tile.get(1).getCharacter()) {
                case EmptyTile.character:
                case GoalTile.character: {  // goal tile and empty have the same attributes
                    // Put a single explosion into the processing queue
                    animation.add(new AnimationModule(tile.get(0).getX(), tile.get(0).getY(), middle));
                    animation.add(new AnimationModule(tile.get(1).getX(), tile.get(1).getY(), edge));
                    // Location point to find two grids
                    deathCheck.add(new int[]{tile.get(0).getX(), tile.get(0).getY(), damagedCount});
                    deathCheck.add(new int[]{tile.get(1).getX(), tile.get(1).getY(), damagedCount});
                    // Death call, search for two cells
                    break;
                }
                // The wall explosion will be removed, but the player or enemy in the place will still cause damage
                case BrokenWall.character: {
                    animation.add(new AnimationModule(tile.get(0).getX(), tile.get(0).getY(), middle));
                    animation.add(new AnimationModule(tile.get(1).getX(), tile.get(1).getY(), edge));
                    database[tile.get(1).getY()][tile.get(1).getX()].die(); // Delete the object in the database
                    deathCheck.add(new int[]{tile.get(0).getX(), tile.get(0).getY(), damagedCount});
                    deathCheck.add(new int[]{tile.get(1).getX(), tile.get(1).getY(), damagedCount});
                    // Death call, search for two cells
                    break;
                }
                // The solid wall cannot be destroyed
                case SolidWall.character: {
                    animation.add(new AnimationModule(tile.get(0).getX(), tile.get(0).getY(), edge));
                    deathCheck.add(new int[]{tile.get(0).getX(), tile.get(0).getY(), damagedCount});
                    //  Death call, search for a cell
                    break;
                }
            }
            // If explosion encounter a solid wall in the first step, just add the central
        } else if (tile.get(0).getCharacter().equals(BrokenWall.character)) {
            animation.add(new AnimationModule(tile.get(0).getX(), tile.get(0).getY(), edge));
            database[tile.get(0).getY()][tile.get(0).getX()].die();
        }
    }
}
