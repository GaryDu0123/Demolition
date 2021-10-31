package demolition;


import demolition.battle.Bomb;
import demolition.battle.Explosion;
import demolition.core.DynamicObject;
import demolition.core.Resource;
import demolition.enums.Direction;
import demolition.enums.GameStatus;
import demolition.role.Enemy;
import demolition.tile.*;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;


/**
 * The main game program, running entry, responsible for the running logic of the game, drawing graphics
 */
public class App extends PApplet {
    /**
     * The initial idea is to call the update method according to the level load and level changes.
     * The current stage of the game.
     */
    private int level = 0;

    /**
     * Windows width {@value}.
     */
    public static final int WIDTH = 480;

    /**
     * Windows height {@value}.
     */
    public static final int HEIGHT = 480;

    /**
     * Frame rate {@value}.
     */
    public static final int FPS = 60;

    /**
     * init config file name
     */
    public static String configName = "config.json";

    public App() {
    }


    /**
     * setting() - setup() - draw()
     * only for setup windows size
     */
    @Override
    public void settings() {
        size(WIDTH, HEIGHT);
    }

    /**
     * Load all static resources in this class, and set the running frame rate, complete all necessary settings.
     *
     * @see Resource#setup(PApplet, String)
     */
    @Override
    public void setup() {
        frameRate(FPS); // 游戏刷新帧率
        Resource.setup(this, configName);
        // set font as load font
        this.textFont(Resource.pFont);
        // fill color for the font
        this.fill(0);
        // The first time the database is initialized as the first map, it will only take effect when the level is updated later.
        Resource.mapStaticRecourseInitUpdate(level, this);
    }

    /**
     * Set the read configuration file, the game loads the map according to the configuration file.
     *
     * @param configName The name of config file.
     */
    public void setConfigName(String configName) {
        App.configName = configName;
    }

    /**
     * Render map objects in the database on the map.
     */
    private void mapStaticGraphUpdate() {
        for (Tile[] tiles : Resource.mapDatabase) {
            for (Tile tile : tiles) {
                image(tile.getImage(), 32 * tile.getX(), 64 + 32 * tile.getY());
            }
        }
    }

    private boolean flag = false;

    /**
     * Determine the state of the game, if the player is on the goal tile or life < 0,
     * the game is over, and the UI is displayed
     */
    private void gameStatusChanger() {

        for (GoalTile goalTile : Resource.goalTile) {
            if (goalTile.getX() == Resource.player.getX() && goalTile.getY() == Resource.player.getY()) {
                flag = true;
                break;
            }
        }
        if (flag) {
            flag = false;
            level++;
            if (level < Resource.levelPathList.size()) {
                Resource.mapStaticRecourseInitUpdate(level, this);


            } else {
                background(239, 129, 0); // Set the background color to orange and update it every time
                Resource.gameStatus = GameStatus.WIN;
                this.text("YOU WIN", 160, 240);
            }
        } else if (Resource.timer <= 0 || Resource.player.getLives() <= 0) {
            background(239, 129, 0); // Set the background color to orange and update it every time
            Resource.gameStatus = GameStatus.LOSE;
            this.text("YOU LOSE", 160, 240);
        }
    }

    /**
     * When the framerate is set to 60, the function will be executed at a rate of 60 times per second.
     * Render all graphics and complete all judgments in this function.
     */
    @Override
    public void draw() {

        background(239, 129, 0); // Set the background color to orange and update it every time.

        gameStatusChanger(); // change the game status, to over or win.

        if (Resource.gameStatus != null) {  // if game already over, return at this time.
            return;
        }

        Resource.UI.show(); // show the UI.

        mapStaticGraphUpdate(); // Show current map status base on the database.

        ArrayList<DynamicObject> processList = new ArrayList<>();
        // Sort the displayed resources for hierarchical display
        Collections.sort(Resource.dynamicObjects);

        // use iterator for convenience remove object
        Iterator<DynamicObject> iterator = Resource.dynamicObjects.iterator();
        while (iterator.hasNext()) {
            DynamicObject object = iterator.next(); // Call the next element of the iterator

            if (object instanceof Enemy) // if object is enemy, cast to enemy
                ((Enemy) object).move(Direction.AUTO, Resource.mapDatabase);

            // If the enemy dies, it will return an object to remove
            DynamicObject rmObject = object.draw();
            if (rmObject != null) {
                if (rmObject instanceof Bomb) { // if belong to bomb
                    // Hand over the explosion to the explosion module
                    processList.add(new Explosion(rmObject.getX(), rmObject.getY(),
                            Resource.mapDatabase, Resource.player, Resource.dynamicObjects, this));
                }
                iterator.remove(); // remove itself
            }
        }
        // Add the generated object to the processing queue.
        Resource.dynamicObjects.addAll(processList);
    }

    /**
     * Save the state of keyboard press. The control is executed only once every time the button is pressed.
     */
    private boolean actionComplete = true; // In order to perform the action only once

    /**
     * Start when the button is pressed, move or drop a bomb according to the user's keyboard input.
     */
    @Override
    public void keyPressed() {
        // 玩家移动处理部分
        if (actionComplete && Resource.gameStatus == null) {
            switch (keyCode) {
                // Move characters based on user input
                case LEFT: // Constant define in processing.core.PConstants
                    Resource.player.move(Direction.DIRECTION_LEFT, Resource.mapDatabase);
                    actionComplete = false;
                    break;
                case RIGHT:
                    Resource.player.move(Direction.DIRECTION_RIGHT, Resource.mapDatabase);
                    actionComplete = false;
                    break;
                case UP:
                    Resource.player.move(Direction.DIRECTION_UP, Resource.mapDatabase);
                    actionComplete = false;
                    break;
                case DOWN:
                    Resource.player.move(Direction.DIRECTION_DOWN, Resource.mapDatabase);
                    actionComplete = false;
                    break;
                case 32: // type space to place a bomb
                    Bomb bomb = new Bomb(Resource.player.getX(), Resource.player.getY(), Resource.bombImg, this);
                    Resource.dynamicObjects.add(bomb);
                    break;
            }
        }
    }

    /**
     * Called when the button is lifted, set actionComplete to true,
     * which means that the execution of a button is completed.
     */
    @Override
    public void keyReleased() {
        actionComplete = true;
    }

    /**
     * Program entry
     *
     * @param args Default incoming parameters
     */
    public static void main(String[] args) {
        PApplet.main("demolition.App");
    }
}
